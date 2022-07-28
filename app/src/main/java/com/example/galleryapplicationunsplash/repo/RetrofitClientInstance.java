package com.example.galleryapplicationunsplash.repo;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitClientInstance {

    private static RetrofitClientInstance instance = null;
    private static Retrofit retrofit = null;
    private final static String BASE_URL = "https://api.unsplash.com";
    private final static String APIKEY = "9qQ2b3hOLsdJ8MFtaHl-iGXB-6GzyC1E872EAXHDwQ4";

    private RetrofitClientInstance(){

    }

    private static Retrofit getRetrofit(){
        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                                                .addInterceptor(new Interceptor() {
                                                    @Override
                                                    public Response intercept(Chain chain) throws IOException {
                                                        Request request = chain.request();
                                                        request = request.newBuilder()
                                                                .addHeader("Authorization", "Client-ID " + APIKEY)
                                                                .build();
                                                        return chain.proceed(request);
                                                    }
                                                }).build();
            retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(okHttpClient)
                                .build();
        }
        return retrofit;
    }

    public static GetData getClient(){
        GetData client = getRetrofit().create(GetData.class);
        return client;
    }
}
