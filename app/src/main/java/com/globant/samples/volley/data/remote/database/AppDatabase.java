package com.globant.samples.volley.data.remote.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by miller.barrera
 */


@Database(entities = {GithubUserRepo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "repository-db";

    public abstract GithubUserDao userDao();
}
