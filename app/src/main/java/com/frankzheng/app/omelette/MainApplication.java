package com.frankzheng.app.omelette;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.frankzheng.app.omelette.net.Network;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class MainApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        //initialize network infrastructure
        Network.getInstance().init();

        Fresco.initialize(this);
    }
}
