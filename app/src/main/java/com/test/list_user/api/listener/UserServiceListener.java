package com.test.list_user.api.listener;

import com.test.list_user.model.response.UserDataResponse;

/**
 * Created by Rafael Quiles
 */

public interface UserServiceListener {

    void onUserResponse(final UserDataResponse response);

    void onUserFailure(final Throwable t);
}
