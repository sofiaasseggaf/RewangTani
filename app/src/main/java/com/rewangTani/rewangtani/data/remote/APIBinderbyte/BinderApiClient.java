package com.rewangTani.rewangtani.data.remote.APIBinderbyte;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BinderApiClient {

    private static final String BASE_URL = "https://api.binderbyte.com/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
