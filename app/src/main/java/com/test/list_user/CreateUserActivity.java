package com.test.list_user;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.test.list_user.api.listener.CreateUserServiceListener;
import com.test.list_user.api.listener.UserServiceListener;
import com.test.list_user.api.service.UserServiceImpl;
import com.test.list_user.model.request.UserRequest;
import com.test.list_user.model.response.UserDataResponse;
import com.test.list_user.model.response.UserResponse;
import com.test.list_user.util.ViewUtil;

import butterknife.OnClick;

public class CreateUserActivity extends BaseActivity implements CreateUserServiceListener {

    Toolbar toolbar;
    LinearLayout btnCreate;
    EditText etJob;
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        this.initializeComponent();
    }

    private void initializeComponent() {
        this.toolbar = findViewById(R.id.toolbar);
        this.btnCreate = findViewById(R.id.btnCreate);
        this.etJob = findViewById(R.id.etJob);
        this.etName = findViewById(R.id.etName);
        // Set Toolbar
        this.toolbar.setTitle(R.string.text_create_user);
        this.setupToolBar(toolbar, android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.btnCreate)
    public void btnCreateClick(View v) {
        //Valid Field Name and ob
        if (isValidForm2()) {
            // Call a ws to authenticate user
            final String name = etName.getText().toString();
            final String job = etJob.getText().toString();
            //Close Keyboard
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etJob.getWindowToken(), 0);
            this.createUser(name, job);
        }
    }

    public boolean isValidForm2() {
        String name = this.etName.getText().toString();
        String job = this.etJob.getText().toString();
        boolean flag = true;
        if (name.isEmpty()) {
            etName.setError("Name is required");
            flag = false;
        }

        if (job.isEmpty()) {
            etJob.setError("Job is required");
            flag = false;
        }
        return flag;
    }

    public void createUser(final String name, final String job){
        if (this.getApp().isOnline()) {
            UserServiceImpl userService = new UserServiceImpl(this);
            userService.setCreateUserServiceListener(this);
            UserRequest userRequest = new UserRequest();
            userRequest.setName(name);
            userRequest.setJob(job);
            userService.createUser(getString(R.string.base_url), userRequest);
        } else {
            ViewUtil.showSnackBar(this, R.string.connection_alert_dialog_tittle, Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);
        }
    }

    @Override
    public void onCreateUserResponse(UserResponse response) {
        etName.setText("");
        etJob.setText("");
        ViewUtil.showSnackBar(this, getString(R.string.text_user_create_susccess) +" "+ response.getCreatedAt(), Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.colorPrimary);
    }

    @Override
    public void onCreateUserFailure(Throwable t) {
        ViewUtil.showSnackBar(this, getString(R.string.text_error_create_user), Snackbar.LENGTH_SHORT, this.findViewById(android.R.id.content), R.color.folder_red);
    }
}
