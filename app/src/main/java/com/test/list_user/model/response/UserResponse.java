package com.test.list_user.model.response;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Rafael Quiles
 */

public class UserResponse implements Serializable {

    private Integer id;
    private String first_name;
    private String last_name;
    private String avatar;
    private Bitmap image;
    private String job;
    private String createdAt;

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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getJob() {
        return job;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
