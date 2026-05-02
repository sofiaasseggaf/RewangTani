package com.rewangTani.rewangtani.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.AkunDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.data.entity.akun.DatumAkun;
import com.rewangTani.rewangtani.data.entity.akun.ModelAkun;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.profilakun.ModelProfilAkun;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AkunRepo
{

    private Context context;
    private AkunDao akunDao;
    private APIInterfacesRest apiInterface;

    public AkunRepo(Context context)
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);
        this.context = context.getApplicationContext();
        akunDao = db.akunDao();
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    }

    public LiveData<List<DatumAkun>> getAccounts()
    {
        refreshFromApi();
        return akunDao.getAllAccount();
    }

    public void loadAccounts(Callback<ModelAkun> callback)
    {
        apiInterface.getDataAkun().enqueue(callback);
    }

    public void loadProfileAccounts(Callback<ModelProfilAkun> callback)
    {
        apiInterface.getDataProfilAkun().enqueue(callback);
    }

    public void sendDataAkun(RequestBody body, Callback<ResponseBody> callback)
    {
        apiInterface.sendDataAkun(body).enqueue(callback);
    }

    public void sendDataProfilAkun(RequestBody body, Callback<ResponseBody> callback)
    {
        apiInterface.sendDataProfilAkun(body).enqueue(callback);
    }

    public void saveAkun(DatumAkun datumAkun)
    {
        PreferenceUtils.savePassword(datumAkun.getPassword(), context);
        PreferenceUtils.saveUsername(datumAkun.getUserName(), context);
        PreferenceUtils.saveIDGoogle(datumAkun.getIdGoogle(), context);
    }

    public void saveProfilAkun(DatumProfil datumProfil)
    {
        PreferenceUtils.saveIdAkun(datumProfil.getIdAkun(), context);
        PreferenceUtils.saveIdProfil(datumProfil.getIdProfile(), context);
        PreferenceUtils.saveIdAlamat(datumProfil.getIdAlamat(), context);
        PreferenceUtils.saveNamaDepan(datumProfil.getNamaDepan(), context);
        PreferenceUtils.saveNamaBelakang(datumProfil.getNamaBelakang(), context);
        if (!datumProfil.getFoto().equalsIgnoreCase("")) {
            PreferenceUtils.saveIDPhoto(datumProfil.getFoto(), context);
        }
    }

    private void refreshFromApi()
    {
        apiInterface.getDataAkun().enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumAkun> accounts = response.body().getData();
                        long now = System.currentTimeMillis();
                        for (DatumAkun p : accounts) {
                            p.lastUpdated = now;
                            Log.i("SOFIA", "AkunRepo - now = " + response.body().getTotalData());
                        }

                        akunDao.deleteAll();
                        akunDao.insertAll(accounts);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
