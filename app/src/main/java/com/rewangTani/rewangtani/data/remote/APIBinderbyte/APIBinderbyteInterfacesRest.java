package com.rewangTani.rewangtani.data.remote.APIBinderbyte;

import com.rewangTani.rewangtani.model.wilayah.BaseResponse;
import com.rewangTani.rewangtani.model.wilayah.City;
import com.rewangTani.rewangtani.model.wilayah.District;
import com.rewangTani.rewangtani.model.wilayah.Province;
import com.rewangTani.rewangtani.model.wilayah.Village;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIBinderbyteInterfacesRest {

    @GET("wilayah/provinsi")
    Call<BaseResponse<Province>> getProvinsi(
            @Query("api_key") String apiKey
    );

    @GET("wilayah/kabupaten")
    Call<BaseResponse<City>> getKabupaten(
            @Query("api_key") String apiKey,
            @Query("id_provinsi") String idProvinsi
    );

    @GET("wilayah/kecamatan")
    Call<BaseResponse<District>> getKecamatan(
            @Query("api_key") String apiKey,
            @Query("id_kabupaten") String idKabupaten
    );

    @GET("wilayah/kelurahan")
    Call<BaseResponse<Village>> getKelurahan(
            @Query("api_key") String apiKey,
            @Query("id_kecamatan") String idKecamatan
    );

}