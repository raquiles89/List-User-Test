package com.test.list_user;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.test.list_user.helper.DBHelper;
import com.test.list_user.module.MainModule;

import dagger.ObjectGraph;

/**
 * Created by rquiles
 * on 10/06/16.
 */
public class MainApplication extends Application {
    // Object for DI
    private ObjectGraph objectGraph;
    // Context for application
    private static Context context;
    private DBHelper mDBHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.context = getApplicationContext();
        this.mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        this.initInjection();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Inject object
     */
    public void inject(final Object object) {
        objectGraph.inject(object);
    }

    public static Context getAppContext() {
        return MainApplication.context;
    }

    /**
     * Initialize injections
     */
    private void initInjection() {
        final MainModule mainModule = new MainModule(getBaseContext());
        objectGraph = ObjectGraph.create(mainModule);
        objectGraph.inject(this);
        objectGraph.injectStatics();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
