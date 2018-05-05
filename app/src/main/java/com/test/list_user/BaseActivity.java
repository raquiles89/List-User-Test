package com.test.list_user;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    public void startRefreshingIndicator(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void stopRefreshingIndicator(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setupToolBar(Toolbar toolbar,final int id) {
        setSupportActionBar(toolbar);
        final Drawable upArrow = ContextCompat.getDrawable(this, id);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.status_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Navigate to another activity
     */
    public void startActivity(final Context fromContext, final Class<?> toContext, final boolean clearStack, final Bundle bundle) {
        Intent intent = new Intent(fromContext, toContext);
        if (clearStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


}
