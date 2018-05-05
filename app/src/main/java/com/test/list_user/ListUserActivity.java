package com.test.list_user;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;

import com.test.list_user.api.listener.UserServiceListener;
import com.test.list_user.api.service.UserServiceImpl;
import com.test.list_user.model.request.UserRequest;
import com.test.list_user.model.response.UserDataResponse;
import com.test.list_user.model.response.UserResponse;
import com.test.list_user.util.ViewUtil;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class ListUserActivity extends BaseActivity implements UserServiceListener {


    ImageView imageView;
    UserDataResponse userDataResponse;
    List<Bitmap> avatarList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        this.avatarList = new ArrayList<>();
        this.imageView= findViewById(R.id.imageView);
        this.loadListUser();
    }

    private void loadListUser() {
        if (this.getApp().isOnline()) {
            //this.progressBar.setVisibility(View.VISIBLE);
            UserServiceImpl userService = new UserServiceImpl(this);
            userService.setUserServiceListener(this);
            UserRequest userRequest = new UserRequest();
            userRequest.setPage("2");
            userService.listUser(getString(R.string.base_url), userRequest);
        } else {
            ViewUtil.showSnackBar(this, R.string.connection_alert_dialog_tittle, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);
        }
    }

    @Override
    public void onUserResponse(UserDataResponse response) {
        if (response != null) {
            this.userDataResponse = response;
            if (this.userDataResponse.getData() != null && !this.userDataResponse.getData().isEmpty()) {
                for (UserResponse user : this.userDataResponse.getData()) {
                    this.getBitmap(user.getAvatar());
                }
            }
        }
        ViewUtil.showSnackBar(this, R.string.text_success_downlaod_model, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);
    }

    @Override
    public void onUserFailure(Throwable t) {
        //this.progressBar.setVisibility(View.GONE);
        ViewUtil.showSnackBar(this, R.string.text_error_downlaod_model, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);

    }


    @SuppressLint("StaticFieldLeak")
    public void getBitmap(final String url) {

        new AsyncTask<String, Bitmap, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    InputStream is = new URL(url).openStream();
                    // Decode Bitmap
                    Bitmap d = BitmapFactory.decodeStream(is);
                    is.close();
                    return d;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    avatarList.add(bitmap);
                }
                imageView.setImageBitmap(bitmap);
            }
        }.execute();
    }

}
