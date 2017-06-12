package com.globant.samples.volley.injection.component;

import android.content.Context;

import com.globant.samples.volley.injection.module.ActivityModule;
import com.globant.samples.volley.injection.qualifier.ActivityContext;
import com.globant.samples.volley.injection.scope.PerActivity;
import com.globant.samples.volley.ui.activities.base.BaseActivity;
import com.globant.samples.volley.ui.activities.details.UserDetailActivity;
import com.globant.samples.volley.ui.activities.user.GithubUserActivity;

import dagger.Component;

/**
 * Created by miller.barrera on 14/10/2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //Inject all activities

    void inject(BaseActivity baseActivity);

    void inject(GithubUserActivity githubUserActivity);

    void inject(UserDetailActivity userDetailActivity);


    @ActivityContext
    Context context();
}
