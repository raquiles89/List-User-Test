package com.test.list_user;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.test.list_user.api.listener.UserServiceListener;
import com.test.list_user.api.service.UserServiceImpl;
import com.test.list_user.model.request.UserRequest;
import com.test.list_user.model.response.UserDataResponse;
import com.test.list_user.util.ViewUtil;

public class ListUserActivity extends BaseActivity implements UserServiceListener{

    UserDataResponse userDataResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        this.loadListUser();
    }

    private void loadListUser() {
        if (this.getApp().isOnline()) {
            //this.progressBar.setVisibility(View.VISIBLE);
            UserServiceImpl userService = new UserServiceImpl(this);
            userService.setUserServiceListener(this);
            UserRequest userRequest = new UserRequest();
            userRequest.setPage(1);
            userService.listUser(getString(R.string.base_url), userRequest);
        } else {
            ViewUtil.showSnackBar(this, R.string.connection_alert_dialog_tittle, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);
        }
    }

    @Override
    public void onUserResponse(UserDataResponse response) {
        ViewUtil.showSnackBar(this, R.string.text_success_downlaod_model, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);
    }

    @Override
    public void onUserFailure(Throwable t) {
        //this.progressBar.setVisibility(View.GONE);
        ViewUtil.showSnackBar(this, R.string.text_error_downlaod_model, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);

    }
}
