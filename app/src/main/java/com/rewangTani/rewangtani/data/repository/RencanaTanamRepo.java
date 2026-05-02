package com.rewangTani.rewangtani.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.RencanaTanamDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RencanaTanamRepo
{

    private Context context;
    private RencanaTanamDao rencanaTanamDao;
    private APIInterfacesRest apiInterfaces;

    public RencanaTanamRepo(Context context)
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);
        this.context = context.getApplicationContext();
        rencanaTanamDao = db.rencanaTanamDao();
    }

    public LiveData<List<DatumRencanaTanam>> getAllRencanaTanam()
    {
        refreshFromApi();
        return rencanaTanamDao.getAllRencanaTanam();
    }

    private void refreshFromApi()
    {
        String idProfil = PreferenceUtils.getIdProfil(context);
        apiInterfaces.getDataRencanaTanamByProfilId(idProfil).enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumRencanaTanam> rencanaTanams = response.body().getData();
                        long now = System.currentTimeMillis();
                        for (DatumRencanaTanam p : rencanaTanams) {
                            p.lastUpdated = now;
                            Log.i("SOFIA", "AkunRepo - now = " + response.body().getTotalData());
                        }

                        rencanaTanamDao.deleteAll();
                        rencanaTanamDao.insertAll(rencanaTanams);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
