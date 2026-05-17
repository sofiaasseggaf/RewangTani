package com.rewangTani.rewangtani.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.entity.product.DataProdukById;
import com.rewangTani.rewangtani.data.entity.product.DatumProduk;
import com.rewangTani.rewangtani.data.entity.product.ModelProduk;
import com.rewangTani.rewangtani.data.entity.warungbpp.DatumBpp;
import com.rewangTani.rewangtani.data.entity.warungbpp.ModelBpp;
import com.rewangTani.rewangtani.data.entity.warungsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.data.entity.warungsewamesin.ModelSewaMesin;
import com.rewangTani.rewangtani.data.entity.warungtenagakerja.DatumTenagaKerja;
import com.rewangTani.rewangtani.data.entity.warungtenagakerja.ModelTenagaKerja;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.ProdukDao;
import com.rewangTani.rewangtani.data.local.dao.WarungBppDao;
import com.rewangTani.rewangtani.data.local.dao.WarungSewaMesinDao;
import com.rewangTani.rewangtani.data.local.dao.WarungTenagaKerjaDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukRepo
{

    private ProdukDao produkDao;
    private WarungBppDao warungBppDao;
    private WarungSewaMesinDao warungSewaMesinDao;
    private WarungTenagaKerjaDao warungTenagaKerjaDao;
    private APIInterfacesRest apiInterface;

    public ProdukRepo(Context context)
    {
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        RewangTaniDB db = RewangTaniDB.getInstance(context);

        produkDao = db.produkDao();
        warungBppDao = db.warungBppDao();
        warungSewaMesinDao = db.warungSewaMesinDao();
        warungTenagaKerjaDao =db.warungTenagaKerjaDao();
    }

    public LiveData<List<DatumProduk>> getProducts()
    {
        refreshFromApi();
        return produkDao.getAllProducts();
    }

    public void loadProducts(Callback<ModelProduk> callback)
    {
        apiInterface.getDataProduk().enqueue(callback);
    }

    public void sendDataProduct(RequestBody body, Callback<DataProdukById> callback)
    {
        apiInterface.sendProduk(body).enqueue(callback);
    }

    private void refreshFromApi()
    {
        apiInterface.getDataProduk().enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumProduk> accounts = response.body().getData();
                        long now = System.currentTimeMillis();
                        for (DatumProduk p : accounts) {
                            p.lastUpdated = now;
                        }

                        produkDao.deleteAll();
                        produkDao.insertAll(accounts);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                t.printStackTrace();
            }
        });

        apiInterface.getDataWarungBibitPupukPestisida().enqueue(new Callback<ModelBpp>() {
            @Override
            public void onResponse(Call<ModelBpp> call, Response<ModelBpp> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumBpp> bpps = response.body().getData();
                        warungBppDao.deleteAll();
                        warungBppDao.insertAll(bpps);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelBpp> call, Throwable t) {
                t.printStackTrace();
            }
        });

        apiInterface.getDataWarungSewaMesin().enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumSewaMesin> sewaMesins = response.body().getData();
                        warungSewaMesinDao.deleteAll();
                        warungSewaMesinDao.insertAll(sewaMesins);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                t.printStackTrace();
            }
        });

        apiInterface.getDataWarungTenagaKerja().enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumTenagaKerja> tenagaKerjas = response.body().getData();
                        warungTenagaKerjaDao.deleteAll();
                        warungTenagaKerjaDao.insertAll(tenagaKerjas);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
