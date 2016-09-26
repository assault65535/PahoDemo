package com.tnecesoc.pahodemo;

import android.app.Application;

/**
 * Created by Tnecesoc on 2016/9/9.
 */
public class PahoDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BrokerConnector.setAndroidContext(this);
    }
}
