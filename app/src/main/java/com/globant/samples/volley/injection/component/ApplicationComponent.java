package com.globant.samples.volley.injection.component;


import android.app.Application;
import android.content.Context;

import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.data.repository.UserRepository;
import com.globant.samples.volley.injection.module.ApplicationModule;
import com.globant.samples.volley.injection.module.NetworkModule;
import com.globant.samples.volley.injection.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by miller.barrera on 14/10/2016.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {


    Application application();

    @ApplicationContext
    Context context();

    DataManager dataManager();

    UserRepository userRepository();

}
