package com.rewangTani.rewangtani.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.ProfilDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.profilakun.ModelProfilAkun;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilRepo {

    private ProfilDao profilDao;
    private APIInterfacesRest apiInterface;

    public ProfilRepo(Context context)
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);
        profilDao = db.profilDao();
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    }

    public LiveData<List<DatumProfil>> getProfiles()
    {
        refreshFromApi();
        return profilDao.getAllProfiles();
    }

    public void loadProfiles(Callback<ModelProfilAkun> callback)
    {
        apiInterface.getDataProfilAkun().enqueue(callback);
    }

    public void sendDataProfile(RequestBody body, Callback<ResponseBody> callback)
    {
        apiInterface.sendDataProfilAkun(body).enqueue(callback);
    }

    private void refreshFromApi()
    {
        apiInterface.getDataProfilAkun().enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumProfil> accounts = response.body().getData();
                        long now = System.currentTimeMillis();
                        for (DatumProfil p : accounts) {
                            p.lastUpdated = now;
                            Log.i("SOFIA", "ProfilRepo - now = " + response.body().getTotalData());
                        }

                        profilDao.deleteAll();
                        profilDao.insertAll(accounts);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
