package com.aswifter.material;

import android.app.Application;

import com.aswifter.material.common.AppClient;
import com.aswifter.material.utils.DisplayUtil;

/**
 * Created by erfli on 6/14/16.
 */
public class MaterialApplication extends Application{
    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AppClient.initAppClient();
        DisplayUtil.init(this);
    }
}
