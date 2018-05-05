package com.test.list_user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.OnClick;

public class CreateUserActivity extends BaseActivity {

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
    }

    @OnClick(R.id.btnCreate)
    public void btnCreateClick(View v) {

        if (isValidForm2()) {
            // Call a ws to authenticate user
            final String name = etName.getText().toString();
            final String job = etJob.getText().toString();
            //this.login(userName, password);
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
}
