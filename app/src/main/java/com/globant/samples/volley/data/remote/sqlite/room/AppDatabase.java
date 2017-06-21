package com.globant.samples.volley.data.remote.sqlite.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;

/**
 * Created by miller.barrera
 */

@Database(entities = {GithubUserRepo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    static final String DATABASE_NAME = "repository-db";

    public abstract GithubUserDao userDao();
}
