package com.frankzheng.app.omelette.net;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class Network {
    private static Network sInstance = new Network();

    private Retrofit retrofit;
    private JandanService jandanService;

    public static Network getInstance() {
        return sInstance;
    }

    public void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://i.jandan.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        jandanService = retrofit.create(JandanService.class);
    }

    public JandanService getJandanService() {
        return jandanService;
    }




}
