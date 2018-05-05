package com.test.list_user.module;

import android.content.Context;

import com.test.list_user.CreateUserActivity;
import com.test.list_user.ListUserActivity;
import com.test.list_user.MainApplication;

import dagger.Module;

@Module(injects = {
        MainApplication.class,
        ListUserActivity.class,
        CreateUserActivity.class,

})
public class MainModule {

    protected Context context;

    public MainModule(final Context context) {
        this.context = context;
    }
}
