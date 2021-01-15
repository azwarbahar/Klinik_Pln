package com.example.klinik_pln.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServerUser {


    private final static String LINK_API = Url_IP.ALAMAT_IP + "antrian_pasien_pln/user/";
//    private final static String LINK_API = Url_IP.ALAMAT_IP + "user/";

    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(LINK_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
