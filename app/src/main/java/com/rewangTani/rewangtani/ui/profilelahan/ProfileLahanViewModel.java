package com.rewangTani.rewangtani.ui.profilelahan;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rewangTani.rewangtani.model.wilayah.BaseResponse;
import com.rewangTani.rewangtani.model.wilayah.City;
import com.rewangTani.rewangtani.model.wilayah.District;
import com.rewangTani.rewangtani.model.wilayah.Province;
import com.rewangTani.rewangtani.model.wilayah.Village;
import com.rewangTani.rewangtani.data.repository.ProfileLahanRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileLahanViewModel extends AndroidViewModel
{

    ProfileLahanRepo repo;
    public MutableLiveData<List<Province>> provList = new MutableLiveData<>();
    public MutableLiveData<List<City>> kabList = new MutableLiveData<>();
    public MutableLiveData<List<District>> kecList = new MutableLiveData<>();
    public MutableLiveData<List<Village>> kelList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isKabEnabled = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isKecEnabled = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isKelEnabled = new MutableLiveData<>(false);

    public ProfileLahanViewModel(@NonNull Application application)
    {
        super(application);
        repo = new ProfileLahanRepo(application);
    }

    public void loadProvinsi()
    {
        repo.getProvinsi(new Callback<BaseResponse<Province>>() {
            @Override
            public void onResponse(Call<BaseResponse<Province>> call, Response<BaseResponse<Province>> res) {
                provList.setValue(res.body().getValue());
            }
            @Override public void onFailure(Call<BaseResponse<Province>> call, Throwable t) {}
        });
    }

    public void loadKabupaten(String idProv)
    {
        isKabEnabled.setValue(false);
        isKecEnabled.setValue(false);
        isKelEnabled.setValue(false);

        repo.getKabupaten(idProv, new Callback<BaseResponse<City>>() {
            @Override
            public void onResponse(Call<BaseResponse<City>> call, Response<BaseResponse<City>> res) {
                kabList.setValue(res.body().getValue());
                isKabEnabled.setValue(true);
            }
            @Override public void onFailure(Call<BaseResponse<City>> call, Throwable t) {}
        });
    }

    public void loadKecamatan(String idKab)
    {
        isKecEnabled.setValue(false);
        isKelEnabled.setValue(false);

        repo.getKecamatan(idKab, new Callback<BaseResponse<District>>() {
            @Override
            public void onResponse(Call<BaseResponse<District>> call, Response<BaseResponse<District>> res) {
                kecList.setValue(res.body().getValue());
                isKecEnabled.setValue(true);
            }
            @Override public void onFailure(Call<BaseResponse<District>> call, Throwable t) {}
        });
    }

    public void loadKelurahan(String idKec)
    {
        isKelEnabled.setValue(false);

        repo.getKelurahan(idKec, new Callback<BaseResponse<Village>>() {
            @Override
            public void onResponse(Call<BaseResponse<Village>> call, Response<BaseResponse<Village>> res) {
                kelList.setValue(res.body().getValue());
                isKelEnabled.setValue(true);
            }
            @Override public void onFailure(Call<BaseResponse<Village>> call, Throwable t) {}
        });
    }

}
