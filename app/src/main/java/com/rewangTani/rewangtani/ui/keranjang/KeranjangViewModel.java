package com.rewangTani.rewangtani.ui.keranjang;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.rewangTani.rewangtani.data.entity.product.CartWithProduct;
import com.rewangTani.rewangtani.data.entity.product.DatumKeranjangLocal;
import com.rewangTani.rewangtani.data.entity.product.DatumProduk;
import com.rewangTani.rewangtani.data.entity.product.ModelProduk;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.profilakun.ModelProfilAkun;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.KeranjangDao;
import com.rewangTani.rewangtani.data.repository.ProdukRepo;
import com.rewangTani.rewangtani.data.repository.ProfilRepo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangViewModel extends AndroidViewModel
{

    private final ProdukRepo productRepo;
    private final ProfilRepo profileRepo;
    private final KeranjangDao keranjangDao;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final LiveData<List<DatumProduk>> products;
    private final LiveData<List<DatumProfil>> profiles;
    private ModelProduk cachedProducts;
    private ModelProfilAkun cachedProfiles;
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<List<CartWithProduct>> cartItems;
    private final MediatorLiveData<Integer> totalPrice = new MediatorLiveData<>();

    public KeranjangViewModel(@NonNull Application application)
    {
        super(application);

        productRepo = new ProdukRepo(application);
        profileRepo = new ProfilRepo(application);
        keranjangDao = RewangTaniDB.getInstance(application).keranjangDao();
        products = productRepo.getProducts();
        profiles = profileRepo.getProfiles();
        cartItems = keranjangDao.getAllCarts();

        // 💰 Auto calculate total
        totalPrice.addSource(cartItems, list -> {
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
    }

    public LiveData<List<DatumProduk>> getProducts()
    {
        return products;
    }

    public ModelProfilAkun getProfiles()
    {
        return cachedProfiles;
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

    public void loadProfiles()
    {
        isLoading.setValue(true);
        profileRepo.loadProfiles(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(@NonNull Call<ModelProfilAkun> call, @NonNull Response<ModelProfilAkun> response) {
                isLoading.setValue(false);

                if (response.body() != null) {
                    cachedProfiles = response.body();
                    Log.i("SOFIA", "KeranjangVM - cachedProfiles = " + cachedProfiles.getTotalData());
                } else {
                    errorMessage.setValue("Data profiles kosong");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelProfilAkun> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Koneksi error");
            }
        });
    }

    public void addToCart(DatumProduk product)
    {
        executor.execute(() -> {
            if (keranjangDao.isExist(product.idProduk)) {
                keranjangDao.increase(product.idProduk);
            } else {
                keranjangDao.insert(new DatumKeranjangLocal(product.idProduk, 1, false));
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

    public void remove(String productId) {
        executor.execute(() -> keranjangDao.delete(productId));
    }

    public LiveData<Integer> getTotalPrice() {
        return totalPrice;
    }
}
