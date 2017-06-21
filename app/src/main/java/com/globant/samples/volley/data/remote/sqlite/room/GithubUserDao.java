package com.globant.samples.volley.data.remote.sqlite.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.model.repository.GithubUserRepo;

import java.util.List;

import io.reactivex.Flowable;
import rx.Observable;

/**
 * Created by miller.barrera on 16/06/2017.
 */

@Dao
public interface GithubUserDao {

    @Query("SELECT * FROM repository")
    public Observable<List<GithubUserRepo>> getAll();

    @Insert
    void insertAll(List<GithubUserRepo> products);

    @Delete
    void delete(GithubUserRepo user);

}
