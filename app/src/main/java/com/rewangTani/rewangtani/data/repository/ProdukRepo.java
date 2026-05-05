package com.rewangTani.rewangtani.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.entity.product.DatumProduk;
import com.rewangTani.rewangtani.data.entity.product.ModelProduk;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.ProdukDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.data.entity.product.DataProdukById;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukRepo
{

    private ProdukDao produkDao;
    private APIInterfacesRest apiInterface;

    public ProdukRepo(Context context)
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);
        produkDao = db.produkDao();
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
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
                            Log.i("SOFIA", "ProdukRepo - now = " + response.body().getTotalData());
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
    }

}
