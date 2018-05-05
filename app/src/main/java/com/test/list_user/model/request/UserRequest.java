package com.test.list_user.model.request;

import java.io.Serializable;

/**
 * Created by Rafael Quiles
 */

public class UserRequest implements Serializable {

    private String page;
    private String name;
    private String job;

    public void setPage(String page) {
        this.page = page;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
