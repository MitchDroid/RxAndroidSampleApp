package com.globant.samples.volley.ui.userDetails;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.repository.UserReposRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miller.barrera on 20/06/2017.
 */

public class UserDetailViewModel {

    private final UserReposRepository mUserRepository;

    @Inject
    public UserDetailViewModel(UserReposRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
    }

    /**
     * This method implements Retrofit Library using RxAndroid
     * The Composite Subscription is handle
     * directly into Activity
     */
    public Observable<List<GithubUserRepo>> doActionGithubUserRepos(String gitHubUserName) {
        return mUserRepository.getRepositories(gitHubUserName).filter(items -> items != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }


}
