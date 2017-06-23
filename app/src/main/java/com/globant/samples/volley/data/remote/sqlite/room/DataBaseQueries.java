package com.globant.samples.volley.data.remote.sqlite.room;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by miller.barrera on 23/06/2017.
 */

@Singleton
public class DataBaseQueries {

    @Inject
    DatabaseCreator mDatabaseCreator;

    @Inject
    public DataBaseQueries() {
    }

    public void insertData(List<GithubUserRepo> products) {
        // Build the database!
        //ponerla como variable de instancia.
        try {
            mDatabaseCreator.getDatabase().beginTransaction();
            mDatabaseCreator.getDatabase().userDao().insertAll(products);
        } finally {
            mDatabaseCreator.getDatabase().setTransactionSuccessful();
            mDatabaseCreator.getDatabase().endTransaction();
        }
    }

    public List<GithubUserRepo> getAllUserReposList() {
        // Build the database!
        try {
            mDatabaseCreator.getDatabase().beginTransaction();
            return mDatabaseCreator.getDatabase().userDao().getAll();

        } finally {
            mDatabaseCreator.getDatabase().setTransactionSuccessful();
            mDatabaseCreator.getDatabase().endTransaction();
        }
    }

    public List<GithubUserRepo> getReposListByUserName(String githubUserName) {
        // Build the database!
        try {
            mDatabaseCreator.getDatabase().beginTransaction();
            return mDatabaseCreator.getDatabase().userDao().getByUserName(githubUserName);

        } finally {
            mDatabaseCreator.getDatabase().setTransactionSuccessful();
            mDatabaseCreator.getDatabase().endTransaction();
        }
    }
}
