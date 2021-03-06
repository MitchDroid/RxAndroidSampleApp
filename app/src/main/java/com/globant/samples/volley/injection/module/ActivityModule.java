package com.globant.samples.volley.injection.module;

import android.app.Activity;
import android.content.Context;

import com.globant.samples.volley.injection.qualifier.ActivityContext;
import com.globant.samples.volley.ui.userList.GitHubUsersListAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by miller.barrera
 */
@Module
public class ActivityModule {

    private Activity mActivity;


    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    //Inject adapters

    @Provides
    GitHubUsersListAdapter gitHubUsersAdapter() {
        return new GitHubUsersListAdapter(mActivity);
    }

}
