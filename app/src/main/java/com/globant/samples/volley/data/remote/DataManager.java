package com.globant.samples.volley.data.remote;

import com.globant.samples.volley.data.model.user.GithubUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by miller.barrera on 18/10/2016.
 */

@Singleton
public class DataManager {

    private UserApiService mUserTokenApiService;


    @Inject
    public DataManager(UserApiService userTokenApiService
    ) {
        this.mUserTokenApiService = userTokenApiService;
    }


    /******************************************************************
     * Backend for get User Data.
     ******************************************************************/

    /**
     * Get Github Users
     */

    public Observable<GithubUser> getGithubUsers() {
        Timber.d("USERS URL %s ", ApiConstants.BASE_URL_USERS);
        return mUserTokenApiService.doGetUsers();
    }
}
