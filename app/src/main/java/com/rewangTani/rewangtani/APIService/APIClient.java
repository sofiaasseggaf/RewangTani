package com.rewangTani.rewangtani.APIService;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rewangTani.rewangtani.utility.StringConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 1/10/2018.
 */

public class APIClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {

     Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain
                        .request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
//                        .addHeader("X-Api-Key", "7826470ABBA476706DB24D47C6A6ED64")
//                        .addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7ImlkIjoiMSJ9LCJpYXQiOjE1ODAwMTI5MTAsImV4cCI6MTU4MDAxNDcxMH0.ZNIc9vEGe2I_toLGN7rJ_e9HZY895dqt-gs-uxGXBvQ")
                        .build();
                return chain.proceed(newRequest);
            }
        };

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        /*ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                .build();*/

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logging)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
                //.connectionSpecs(Collections.singletonList(spec))
                .build();

        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringConverter());
        gb.setLenient();
        Gson gson = gb.create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://167.172.72.217:8080/tanampadi/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit;


    }
}
