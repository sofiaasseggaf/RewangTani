package com.rewangTani.rewangtani.ui.keranjang;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.rewangTani.rewangtani.data.entity.product.CartItemUI;
import com.rewangTani.rewangtani.data.entity.product.CartWithProduct;
import com.rewangTani.rewangtani.data.entity.product.DatumKeranjangLocal;
import com.rewangTani.rewangtani.data.entity.product.ModelProduk;
import com.rewangTani.rewangtani.data.entity.product.ProductResolver;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.KeranjangDao;
import com.rewangTani.rewangtani.data.local.dao.ProfilDao;
import com.rewangTani.rewangtani.data.local.dao.WarungBppDao;
import com.rewangTani.rewangtani.data.local.dao.WarungSewaMesinDao;
import com.rewangTani.rewangtani.data.local.dao.WarungTenagaKerjaDao;
import com.rewangTani.rewangtani.data.repository.ProdukRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangViewModel extends AndroidViewModel
{

    private final ProdukRepo productRepo;
    private final KeranjangDao keranjangDao;
    private final ProfilDao profilDao;
    private final WarungBppDao warungBppDao;
    private final WarungSewaMesinDao warungSewaMesinDao;
    private final WarungTenagaKerjaDao warungTenagaKerjaDao;
    private final ProductResolver productResolver;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private ModelProduk cachedProducts;
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<List<CartWithProduct>> cartItems;
    private final MediatorLiveData<Integer> totalPrice = new MediatorLiveData<>();
    MediatorLiveData<List<CartItemUI>> cartUI = new MediatorLiveData<>();

    public KeranjangViewModel(@NonNull Application application)
    {
        super(application);

        productRepo = new ProdukRepo(application);
        keranjangDao = RewangTaniDB.getInstance(application).keranjangDao();
        profilDao = RewangTaniDB.getInstance(application).profilDao();
        warungBppDao = RewangTaniDB.getInstance(application).warungBppDao();
        warungSewaMesinDao = RewangTaniDB.getInstance(application).warungSewaMesinDao();
        warungTenagaKerjaDao = RewangTaniDB.getInstance(application).warungTenagaKerjaDao();

        productRepo.getProducts();
        cartItems = keranjangDao.getAllCarts();
        productResolver = new ProductResolver(warungBppDao, warungSewaMesinDao, warungTenagaKerjaDao);

        totalPrice.addSource(cartItems, list ->
        {
            int total = 0;
            if (list != null) {
                for (CartWithProduct item : list) {
                    if (item.product != null) {
                        total += item.product.getHargaProduk() * item.keranjangLocal.quantity;
                    }
                }
            }

            totalPrice.setValue(total);
        });


        cartUI.addSource(cartItems, list ->
        {
            if (list == null) return;
            executor.execute(() ->
            {
                List<CartItemUI> result = new ArrayList<>();

                for (CartWithProduct item : list)
                {
                    DatumProfil profile =
                            profilDao.getProfileByProductIdDirect(item.keranjangLocal.productId);
                    String name =
                            productResolver.resolveName(item.product);

                    CartItemUI ui = new CartItemUI();
                    ui.product = item.product;
                    ui.cart = item.keranjangLocal;
                    ui.profile = profile;
                    ui.productName = name;

                    result.add(ui);
                }

                cartUI.postValue(result);
            });
        });
    }

    public void loadProducts()
    {
        isLoading.setValue(true);
        productRepo.loadProducts(new Callback<ModelProduk>() {
            @Override
            public void onResponse(@NonNull Call<ModelProduk> call, @NonNull Response<ModelProduk> response) {
                isLoading.setValue(false);

                if (response.body() != null) {
                    cachedProducts = response.body();
                    Log.i("SOFIA", "KeranjangVM - cachedProducts = " + cachedProducts.getTotalData());
                } else {
                    errorMessage.setValue("Data products kosong");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelProduk> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Koneksi error");
            }
        });
    }

    public void addToCart(String idProduk)
    {
        executor.execute(() -> {
            if (keranjangDao.isExist(idProduk)) {
                keranjangDao.increase(idProduk);
            } else {
                keranjangDao.insert(new DatumKeranjangLocal(idProduk, 1, false));
            }
        });
    }

    public void increase(String productId)
    {
        executor.execute(() -> keranjangDao.increase(productId));
    }

    public void decrease(String productId)
    {
        executor.execute(() -> {
            int qty = keranjangDao.getQuantity(productId);

            if (qty > 1) {
                keranjangDao.decrease(productId);
            } else {
                keranjangDao.delete(productId);
            }
        });
    }

    public void updateIsChecked(String productId, boolean isChecked)
    {
        executor.execute(() -> {
            keranjangDao.updateIsChecked(productId, isChecked);
        });
    }

    public void remove(String productId)
    {
        executor.execute(() -> keranjangDao.delete(productId));
    }

    public LiveData<Integer> getTotalPrice()
    {
        return totalPrice;
    }

}
