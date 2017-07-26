package com.globant.samples.volley.ui.userDetail;

import android.support.test.espresso.IdlingResource;

import com.globant.samples.volley.ui.userDetails.UserDetailActivity;

/**
 * Created by miller.barrera on 26/07/2017.
 */

public class UserDetailActivityIdlingResource implements IdlingResource {

    private UserDetailActivity mUserDetailActivity;
    private ResourceCallback mResourceCallback;

    public UserDetailActivityIdlingResource(UserDetailActivity mUserDetailActivity) {
        this.mUserDetailActivity = mUserDetailActivity;
    }

    @Override
    public String getName() {
        return "userDetailIdle";
    }

    @Override
    public boolean isIdleNow() {
        Boolean idle = isIdle();
        if (idle) {
            mResourceCallback.onTransitionToIdle();
        }

        return idle;
    }

    private boolean isIdle() {
        return mUserDetailActivity != null && mResourceCallback != null && mUserDetailActivity.isIdleSyncFinished();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mResourceCallback = callback;

    }
}
