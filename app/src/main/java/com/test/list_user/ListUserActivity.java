package com.test.list_user;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.test.list_user.api.listener.UserServiceListener;
import com.test.list_user.api.service.UserServiceImpl;
import com.test.list_user.model.request.UserRequest;
import com.test.list_user.model.response.UserDataResponse;
import com.test.list_user.model.response.UserResponse;
import com.test.list_user.util.ViewUtil;
import com.test.list_user.view.adapter.ItemAdapter;
import com.test.list_user.view.listener.ItemListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ListUserActivity extends BaseActivity implements UserServiceListener, ItemListener, SwipeRefreshLayout.OnRefreshListener {

    ProgressBar progressBar;
    Toolbar toolbar;
    RecyclerView rvUsers;
    RelativeLayout item_pull_refresh;
    SwipeRefreshLayout refresh_user;
    FloatingActionButton floatingActionButton;
    UserDataResponse userDataResponse;
    List<Bitmap> avatarList;
    List<UserResponse> originalUserResponse;
    Integer count;
    ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        this.initializeComponent();
        this.loadListUser();
    }

    private void initializeComponent() {
        this.avatarList = new ArrayList<>();
        this.originalUserResponse = new ArrayList<>();
        this.progressBar = findViewById(R.id.progressBar);
        this.toolbar = findViewById(R.id.toolbar);
        this.refresh_user = findViewById(R.id.refresh_segment);
        this.rvUsers = findViewById(R.id.rv_segment);
        this.item_pull_refresh = findViewById(R.id.item_pull_refresh);
        this.item_pull_refresh.setVisibility(View.GONE);
        this.floatingActionButton = findViewById(R.id.fab);
        // Setup float action button
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ListUserActivity.this, CreateUserActivity.class, false, null);
            }
        });

        this.count = 1;
        // Setup the PullToRefreshLayout
        this.refresh_user.setOnRefreshListener(this);
        this.refresh_user.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // Set Toolbar
        this.toolbar.setTitle(R.string.toolbar_name);
        this.setupToolBar(toolbar, R.mipmap.ic_list_white_24dp);
    }

    private void loadListUser() {
        if (this.getApp().isOnline()) {
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
                    this.originalUserResponse.add(user);
                    this.getBitmap(user.getAvatar(), user.getId());
                }
            }
        }
    }

    @Override
    public void onUserFailure(Throwable t) {
        this.progressBar.setVisibility(View.GONE);
        ViewUtil.showSnackBar(this, R.string.text_error_downlaod_model, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);

    }


    @SuppressLint("StaticFieldLeak")
    public void getBitmap(final String url, final Integer id) {

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
                    UserResponse userResponse = searchUser(id);
                    if (userResponse != null) {
                        int index = originalUserResponse.indexOf(userResponse);
                        userResponse.setImage(bitmap);
                        originalUserResponse.set(index, userResponse);
                    }
                }
                if (count == userDataResponse.getData().size()) {
                    progressBar.setVisibility(View.GONE);
                    setupAdapter();
                    ViewUtil.showSnackBar(ListUserActivity.this, R.string.text_success_downlaod_model, Snackbar.LENGTH_SHORT, findViewById(android.R.id.content), R.color.folder_blue);
                } else {
                    count++;
                }
            }
        }.execute();
    }

    private void setupAdapter() {
        //Setup Adapter
        this.adapter = new ItemAdapter(this, this.originalUserResponse, this);
        this.rvUsers.setAdapter(this.adapter);
        this.rvUsers.setItemAnimator(new DefaultItemAnimator());
        this.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        this.adapter.notifyDataSetChanged();

        if (this.originalUserResponse.isEmpty()) {
            this.item_pull_refresh.setVisibility(View.VISIBLE);
        } else {
            this.item_pull_refresh.setVisibility(View.GONE);
        }
    }

    public UserResponse searchUser(final Integer id) {
        for (UserResponse user : this.originalUserResponse) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void onItemClicked(int position) {
        UserResponse user = this.originalUserResponse.get(position);
        ViewUtil.showSnackBar(ListUserActivity.this, getString(R.string.text_touch_in) + " " + user.getFirst_name() + " " + user.getLast_name(), Snackbar.LENGTH_SHORT, findViewById(android.R.id.content), R.color.folder_blue);
    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }


    @Override
    public void onRefresh() {
        try {
            ViewUtil.showSnackBar(this, R.string.text_refresh_succes, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.colorPrimary);
            this.stopRefreshingIndicator(this.refresh_user);
        } catch (Exception e) {
            e.printStackTrace();
            this.stopRefreshingIndicator(this.refresh_user);
        }
    }
}
