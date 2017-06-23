package com.globant.samples.volley.data.remote.database;

/**
 * Created by miller.barrera on 17/06/2017.
 */


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Creates the {@link AppDatabase} asynchronously, exposing a LiveData object to notify of creation.
 */

@Singleton
public class DatabaseCreator {

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private Context mContext;

    private final AtomicBoolean mInitializing = new AtomicBoolean(true);

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    AppDatabase db;

    @Inject
    public DatabaseCreator() {

    }


    /**
     * Used to observe when the database initialization is done
     */
    public LiveData<Boolean> isDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    @Nullable
    public AppDatabase getDatabase() {
        return db;
    }

    /**
     * Creates or returns a previously-created database.
     * <p>
     * Although this uses an AsyncTask which currently uses a serial executor, it's thread-safe.
     */
    public void createDb(Context context) {
        Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().getName());

        if (!mInitializing.compareAndSet(true, false)) {
            return; // Already initializing
        }

        Observable.fromCallable(() -> {
            Log.d("DatabaseCreator",
                    "Starting bg job " + Thread.currentThread().getName());

            context.getApplicationContext();

            // Reset the database to have new data on every run.
            context.deleteDatabase(AppDatabase.DATABASE_NAME);

            mContext = context;

            db = Room.databaseBuilder(mContext.getApplicationContext(),
                    AppDatabase.class, AppDatabase.DATABASE_NAME).build();

            return null;
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(t -> {
            // Now on the main thread, notify observers that the db is created and ready.
            mIsDatabaseCreated.setValue(true);
        }).subscribe(o -> {
        });

        //mIsDatabaseCreated.setValue(false);// Trigger an update to show a loading screen.
    }

    public void closeDbInstance() {
        db.close();
    }

}
