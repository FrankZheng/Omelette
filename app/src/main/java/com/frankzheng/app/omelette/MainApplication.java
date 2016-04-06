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

    private static final String TAG = MainApplication.class.getSimpleName();

    public static Context context;

    private ILogger logger;

    public void onCreate() {
        super.onCreate();
        context = this;

        logger = LoggerFactory.getInstance().getLogger(TAG);
        logger.i("onCreate");

        //initialize network infrastructure
        Network.getInstance().init();

        Fresco.initialize(this);
    }
}
