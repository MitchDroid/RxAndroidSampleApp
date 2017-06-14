package com.globant.samples.volley;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.globant.samples.volley.common.FakeCrashLibrary;
import com.globant.samples.volley.injection.component.ApplicationComponent;
import com.globant.samples.volley.injection.component.DaggerApplicationComponent;
import com.globant.samples.volley.injection.module.ApplicationModule;
import com.globant.samples.volley.injection.module.NetworkModule;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by miller.barrera on 5/06/2017.
 */

public class ApplicationController extends Application {


    ApplicationComponent mApplicationComponent;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ApplicationController sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the singleton
        sInstance = this;

        Realm.init(this);
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }

    public static ApplicationController get(Context context) {
        return (ApplicationController) context.getApplicationContext();
    }

    /**
     * To get ApplicationComponent
     *
     * @return ApplicationComponent
     */
    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .networkModule(new NetworkModule())
                    .build();
        }
        return mApplicationComponent;
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
