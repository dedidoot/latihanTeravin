package com.test.teravin.latihanteravin;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Dedi on 24/08/2017.
 * Happy coding, buddy!
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
