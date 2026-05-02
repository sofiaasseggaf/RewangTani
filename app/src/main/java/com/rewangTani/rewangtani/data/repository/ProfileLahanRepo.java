package com.rewangTani.rewangtani.data.repository;

import android.content.Context;

import com.rewangTani.rewangtani.model.wilayah.BaseResponse;
import com.rewangTani.rewangtani.model.wilayah.City;
import com.rewangTani.rewangtani.model.wilayah.District;
import com.rewangTani.rewangtani.model.wilayah.Province;
import com.rewangTani.rewangtani.model.wilayah.Village;
import com.rewangTani.rewangtani.data.remote.APIBinderbyte.APIBinderbyteInterfacesRest;
import com.rewangTani.rewangtani.data.remote.APIBinderbyte.BinderApiClient;
import com.rewangTani.rewangtani.utility.Global;

import retrofit2.Callback;

public class ProfileLahanRepo
{

    private Context context;
    private APIBinderbyteInterfacesRest apiBinderbyteInterfacesRest;

    public ProfileLahanRepo(Context context)
    {
        this.context = context;
        apiBinderbyteInterfacesRest = BinderApiClient.getClient().create(APIBinderbyteInterfacesRest.class);
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
