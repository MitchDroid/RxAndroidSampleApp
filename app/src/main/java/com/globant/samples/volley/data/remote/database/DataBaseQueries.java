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
            this.mDatabaseCreator.getDatabase().beginTransaction();
            this.mDatabaseCreator.getDatabase().userDao().insertAll(products);
        } finally {
            this.mDatabaseCreator.getDatabase().setTransactionSuccessful();
            this.mDatabaseCreator.getDatabase().endTransaction();
        }
    }

    public List<GithubUserRepo> getAllUserReposList() {
        try {
            this.mDatabaseCreator.getDatabase().beginTransaction();
            return this.mDatabaseCreator.getDatabase().userDao().getAll();

        } finally {
            this.mDatabaseCreator.getDatabase().setTransactionSuccessful();
            this.mDatabaseCreator.getDatabase().endTransaction();
        }
    }

    public List<GithubUserRepo> getReposListByUserName(String githubUserName) {
        try {
            this.mDatabaseCreator.getDatabase().beginTransaction();
            return this.mDatabaseCreator.getDatabase().userDao().getByUserName(githubUserName);

        } finally {
            this.mDatabaseCreator.getDatabase().setTransactionSuccessful();
            this.mDatabaseCreator.getDatabase().endTransaction();
        }
    }


    public void closeDatabase(){
        this.mDatabaseCreator.closeDbInstance();
    }
    
}
