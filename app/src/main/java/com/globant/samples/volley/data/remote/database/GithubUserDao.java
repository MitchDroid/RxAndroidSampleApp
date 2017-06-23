package com.globant.samples.volley.data.remote.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;

import java.util.List;

/**
 * Created by miller.barrera on 16/06/2017.
 */

@Dao
public interface GithubUserDao {

    @Query("SELECT * FROM repository")
    List<GithubUserRepo> getAll();

    @Query("SELECT * FROM repository where userName = :userName")
    List<GithubUserRepo> getByUserName(String userName);

    @Insert
    void insertAll(List<GithubUserRepo> products);

    @Delete
    void delete(GithubUserRepo user);

}
