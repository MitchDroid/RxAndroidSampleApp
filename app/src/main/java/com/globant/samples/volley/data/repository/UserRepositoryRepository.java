package com.globant.samples.volley.data.repository;

import android.content.Context;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.data.remote.sqlite.room.DatabaseCreator;
import com.globant.samples.volley.injection.qualifier.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by miller.barrera on 21/06/2017.
 */

public class UserRepositoryRepository {

    private DataManager mDataManager;

    @Inject
    DatabaseCreator mDatabaseCreator;

    @Inject
    @ApplicationContext
    Context mContext;

    @Inject
    public UserRepositoryRepository(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public Observable<List<GithubUserRepo>> getRepositories(String githubUserName) {
        mDatabaseCreator.createDb(mContext);
        return mDatabaseCreator.getUserReposList().flatMap(githubUserRepos -> {
            if (!githubUserRepos.isEmpty()) {
               // return Observable.just(githubUserRepos);
            } else {
               ///return getPublicRepositoriesFromServer(githubUserName);
            }
            return null;
        });
    }

    private Observable<List<GithubUserRepo>> getPublicRepositoriesFromServer(String githubUserName) {
        return mDataManager.getGithubUserRepos(githubUserName).filter(githubUserRepo -> githubUserRepo != null)
                .doOnNext(githubUserRepo -> {
                    Timber.d("REPOS SIZE %s", githubUserRepo.size());
                    mDatabaseCreator.createDb(mContext);
                    mDatabaseCreator.insertData(githubUserRepo);

                });

    }
}
