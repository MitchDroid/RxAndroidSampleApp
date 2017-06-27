package com.globant.samples.volley.data.repository;

import android.content.Context;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.data.remote.database.DataBaseQueries;
import com.globant.samples.volley.data.remote.database.DatabaseCreator;
import com.globant.samples.volley.injection.qualifier.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by miller.barrera on 21/06/2017.
 */

public class UserReposRepository {

    private DataManager mDataManager;

    @Inject
    DatabaseCreator mDatabaseCreator;

    @Inject
    DataBaseQueries mDataBaseQueries;

    @Inject
    @ApplicationContext
    Context mContext;

    @Inject
    public UserReposRepository(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public Observable<List<GithubUserRepo>> getRepositories(String githubUserName) {
        mDatabaseCreator.createDb(mContext);
        return Observable.fromCallable(() -> mDataBaseQueries.getReposListByUserName(githubUserName)).flatMap(githubUserRepos -> {
            if (!githubUserRepos.isEmpty()) {
                return Observable.just(githubUserRepos);
            } else {
                return getPublicRepositoriesFromServer(githubUserName);
            }

        });

    }

    private Observable<List<GithubUserRepo>> getPublicRepositoriesFromServer(String githubUserName) {
        return mDataManager.getGithubUserRepos(githubUserName).filter(githubUserRepo -> githubUserRepo != null)
                .doOnNext(githubUserRepo -> {
                    Timber.d("REPOS SIZE %s", githubUserName);
                    for (int i = 0; i < githubUserRepo.size(); i++) {
                        githubUserRepo.get(i).setUserName(githubUserName);
                    }
                    mDatabaseCreator.createDb(mContext);
                    mDataBaseQueries.insertData(githubUserRepo);
                });

    }
}
