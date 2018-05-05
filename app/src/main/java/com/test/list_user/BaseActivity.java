package com.test.list_user;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.test.list_user.helper.DBHelper;

public class BaseActivity extends AppCompatActivity {

    MainApplication app;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjection();
    }

    /**
     * Initialize application injection
     */
    private void initializeInjection() {
        this.app = (MainApplication) getApplication();
        this.app.inject(this);
    }

    public MainApplication getApp() {
        return app;
    }

    /**
     * Show a custom SnackBar notification without action
     */
    public void showSnackBar(final int resourceId, final int duration) {
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), resourceId, duration);
        //Background
        final View snackBarView = snackbar.getView();
        snackBarView.setBackgroundResource(android.R.color.holo_red_light);
        //Message
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    /**
     * Get a helper to access db objects
     */
    public DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        }
        return mDBHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Destroy helper
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
    }
}
