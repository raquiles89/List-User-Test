package com.test.list_user.api.listener;

import com.test.list_user.model.response.UserDataResponse;
import com.test.list_user.model.response.UserResponse;

/**
 * Created by Rafael Quiles
 */

public interface CreateUserServiceListener {

    void onCreateUserResponse(final UserResponse response);

    void onCreateUserFailure(final Throwable t);
}
