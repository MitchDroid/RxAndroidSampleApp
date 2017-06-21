package com.globant.samples.volley.ui.userDetails;

import android.os.HandlerThread;

import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.remote.sqlite.room.DatabaseCreator;
import com.globant.samples.volley.data.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by miller.barrera on 20/06/2017.
 */

public class UserDetailViewModel {

    private final UserRepository mUserRepository;
    private Scheduler scheduler;
    private HandlerThread handlerThread;

    @Inject
    public UserDetailViewModel(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
        handlerThread = new HandlerThread("backgroundThread");
        handlerThread.start();
        scheduler = AndroidSchedulers.from(handlerThread.getLooper());
    }

    /**
     * This method implements Retrofit Library using RxAndroid
     * The Composite Subscription is handle
     * directly into Activity
     */
    public Observable<List<GithubUserRepo>> doActionGithubUserRepos(String gitHubUserName) {
        return mUserRepository.getPublicRepositories(gitHubUserName).filter(items -> items != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(scheduler)
                .unsubscribeOn(scheduler);
    }


}
