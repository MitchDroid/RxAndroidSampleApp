package com.globant.samples.volley.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.globant.samples.volley.data.remote.UserApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by miller.barrera.
 */
@Module
public class NetworkModule {

    protected final String PREF_NAME = "preferences";

    public NetworkModule() {
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public UserApiService userApiService() {
        return UserApiService.Factory.create();
    }

}
