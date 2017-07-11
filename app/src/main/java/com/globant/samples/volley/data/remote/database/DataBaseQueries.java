package com.globant.samples.volley.data.remote.database;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by miller.barrera on 23/06/2017.
 */

@Singleton
public class DataBaseQueries {

    private final DatabaseCreator mDatabaseCreator;

    @Inject
    public DataBaseQueries(DatabaseCreator databaseCreator) {
        this.mDatabaseCreator = databaseCreator;
    }

    public void insertData(List<GithubUserRepo> products) {
        try {
            getAppDatabaseInstance().beginTransaction();
            getAppDatabaseInstance().userDao().insertAll(products);
        } finally {
            getAppDatabaseInstance().setTransactionSuccessful();
            getAppDatabaseInstance().endTransaction();
        }
    }

    public List<GithubUserRepo> getAllUserReposList() {
        try {
            getAppDatabaseInstance().beginTransaction();
            return getAppDatabaseInstance().userDao().getAll();

        } finally {
            getAppDatabaseInstance().setTransactionSuccessful();
            getAppDatabaseInstance().endTransaction();
        }
    }

    public List<GithubUserRepo> getReposListByUserName(String githubUserName) {
        try {
            getAppDatabaseInstance().beginTransaction();
            return getAppDatabaseInstance().userDao().getByUserName(githubUserName);

        } finally {
            getAppDatabaseInstance().setTransactionSuccessful();
            getAppDatabaseInstance().endTransaction();
        }
    }


    /**
     * get the AppDatabase instance from {@link DatabaseCreator}
     *
     * @return AppDatabase instance
     */

    public AppDatabase getAppDatabaseInstance() {
        return mDatabaseCreator.getDatabase();
    }
}
