package com.test.list_user.model.response;

import java.io.Serializable;

/**
 * Created by Rafael Quiles
 */

public class UserResponse implements Serializable {

    private Integer id;
    private String first_name;
    private String last_name;
    private String avatar;

    public Integer getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }
}
