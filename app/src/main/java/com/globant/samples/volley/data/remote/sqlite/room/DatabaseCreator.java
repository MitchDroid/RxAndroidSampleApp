package com.globant.samples.volley.data.remote.sqlite.room;

/**
 * Created by miller.barrera on 17/06/2017.
 */


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.globant.samples.volley.data.remote.sqlite.room.AppDatabase.DATABASE_NAME;

/**
 * Creates the {@link AppDatabase} asynchronously, exposing a LiveData object to notify of creation.
 */

@Singleton
public class DatabaseCreator {

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private AppDatabase mDb;

    private Context mContext;

    private final AtomicBoolean mInitializing = new AtomicBoolean(true);

    // For Singleton instantiation
    private static final Object LOCK = new Object();

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
        return mDb;
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

        //mIsDatabaseCreated.setValue(false);// Trigger an update to show a loading screen.
        new AsyncTask<Context, Void, Void>() {

            @Override
            protected Void doInBackground(Context... params) {
                Log.d("DatabaseCreator",
                        "Starting bg job " + Thread.currentThread().getName());

                Context context = params[0].getApplicationContext();

                // Reset the database to have new data on every run.
                context.deleteDatabase(DATABASE_NAME);

                mContext = context;

                // Add a delay to simulate a long-running operation
                addDelay();

                return null;
            }

            @Override
            protected void onPostExecute(Void ignored) {
                // Now on the main thread, notify observers that the db is created and ready.
                mIsDatabaseCreated.setValue(true);
            }
        }.execute(context.getApplicationContext());
    }

    private void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public  void insertData(List<GithubUserRepo> products) {
        // Build the database!
        AppDatabase db = Room.databaseBuilder(mContext.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME).build();
        db.beginTransaction();
        try {
            db.userDao().insertAll(products);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        mDb = db;
    }

    public Observable<List<GithubUserRepo>> getUserReposList(){
        // Build the database!
        AppDatabase db = Room.databaseBuilder(mContext.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME).build();
        db.beginTransaction();
        try {

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        mDb = db;

        return db.userDao().getAll();

    }
}
