package com.test.list_user.model.request;

import java.io.Serializable;

/**
 * Created by Rafael Quiles
 */

public class UserRequest implements Serializable {

    private String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
