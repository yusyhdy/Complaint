package com.smkpgri2.alaska.service;

import com.smkpgri2.alaska.rest.RestVariable;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = RestVariable.SERVER_URL;


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
