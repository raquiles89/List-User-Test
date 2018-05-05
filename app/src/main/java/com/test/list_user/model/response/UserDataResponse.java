package com.test.list_user.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rafael Quiles
 */

public class UserDataResponse implements Serializable {

    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<UserResponse> data;


    public Integer getPage() {
        return page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public List<UserResponse> getData() {
        return data;
    }
}
