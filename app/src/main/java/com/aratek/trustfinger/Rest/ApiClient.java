package com.aratek.trustfinger.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
<<<<<<< HEAD
    public static final String BASE_URL = "http://192.168.177.81:8088/";
=======
<<<<<<< HEAD
    public static final String BASE_URL = "http://192.168.100.30:9002/";
=======
    public static final String BASE_URL = "http://192.168.43.65:8088/";
>>>>>>> dd0600ed524cea1a3c151086f70cf2b1ea9627f4
>>>>>>> df69a8e9c7042af8cd4ffeb00183a4f508187f37
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
