package com.rewangTani.rewangtani.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.ProfilLahanDao;
import com.rewangTani.rewangtani.data.remote.APIBinderbyte.APIBinderbyteInterfacesRest;
import com.rewangTani.rewangtani.data.remote.APIBinderbyte.BinderApiClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.wilayah.BaseResponse;
import com.rewangTani.rewangtani.model.wilayah.City;
import com.rewangTani.rewangtani.model.wilayah.District;
import com.rewangTani.rewangtani.model.wilayah.Province;
import com.rewangTani.rewangtani.model.wilayah.Village;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileLahanRepo
{

    private Context context;
    private ProfilLahanDao profilLahanDao;
    private APIInterfacesRest apiInterface;
    private APIBinderbyteInterfacesRest apiBinderbyteInterfacesRest;

    public ProfileLahanRepo(Context context)
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);
        this.context = context;

        profilLahanDao = db.profilLahanDao();
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        apiBinderbyteInterfacesRest = BinderApiClient.getClient().create(APIBinderbyteInterfacesRest.class);
    }

    public LiveData<List<DatumProfilLahan>> getAllProfilLahan()
    {
        refreshFromApi();
        return profilLahanDao.getAllProfilLahan();
    }

    private void refreshFromApi()
    {
        String idAkun = PreferenceUtils.getIdAkun(context);
        apiInterface.getDataProfileLahanByAkunId(idAkun).enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumProfilLahan> profilLahans = new ArrayList<>();
                        for (DatumProfilLahan profilLahan : response.body().getData()) {
                            if (profilLahan.getIdUser().equalsIgnoreCase(idAkun)) {
                                profilLahans.add(profilLahan);
                            }
                        }
                        long now = System.currentTimeMillis();
                        for (DatumProfilLahan p : profilLahans) {
                            p.lastUpdated = now;
                        }

                        profilLahanDao.deleteAll();
                        profilLahanDao.insertAll(profilLahans);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getProvinsi(Callback<BaseResponse<Province>> cb)
    {
        apiBinderbyteInterfacesRest.getProvinsi(Global.API_BINDER).enqueue(cb);
    }

    public void getKabupaten(String id, Callback<BaseResponse<City>> cb)
    {
        apiBinderbyteInterfacesRest.getKabupaten(Global.API_BINDER, id).enqueue(cb);
    }

    public void getKecamatan(String id,Callback<BaseResponse<District>> cb)
    {
        apiBinderbyteInterfacesRest.getKecamatan(Global.API_BINDER, id).enqueue(cb);
    }

    public void getKelurahan(String id, Callback<BaseResponse<Village>> cb)
    {
        apiBinderbyteInterfacesRest.getKelurahan(Global.API_BINDER, id).enqueue(cb);
    }

}
