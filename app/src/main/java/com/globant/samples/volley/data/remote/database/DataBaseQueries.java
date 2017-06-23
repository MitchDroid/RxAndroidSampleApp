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

    @Inject
    DatabaseCreator mDatabaseCreator;
    
    AppDatabase db;

    @Inject
    public DataBaseQueries() {
        db = mDatabaseCreator.getDatabase();
    }

    public void insertData(List<GithubUserRepo> products) {
        try {
            db.beginTransaction();
            db.userDao().insertAll(products);
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    public List<GithubUserRepo> getAllUserReposList() {
        try {
            db.beginTransaction();
            return db.userDao().getAll();

        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    public List<GithubUserRepo> getReposListByUserName(String githubUserName) {
        try {
            db.beginTransaction();
            return db.userDao().getByUserName(githubUserName);

        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

}
