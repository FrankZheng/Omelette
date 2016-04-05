package com.frankzheng.app.omelette;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.frankzheng.app.omelette.log.ILogger;
import com.frankzheng.app.omelette.log.LoggerFactory;
import com.frankzheng.app.omelette.net.Network;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class MainApplication extends Application {

    public static Context context;

    private ILogger logger = LoggerFactory.getInstance().getLogger("MainApplication");

    public void onCreate() {
        super.onCreate();
        context = this;

        logger.i("onCreate");

        //initialize network infrastructure
        Network.getInstance().init();

        Fresco.initialize(this);
    }
}
