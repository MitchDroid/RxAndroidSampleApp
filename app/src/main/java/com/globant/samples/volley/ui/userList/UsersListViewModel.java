package com.globant.samples.volley.ui.userList;

import com.globant.samples.volley.data.model.user.GithubUser;
import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public class UsersListViewModel extends BasePresenter<UserListView> {

    private final DataManager mDataManager;

    @Inject
    public UsersListViewModel(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    /**
     * This method implements Retrofit Library using RxAndroid
     * The Composite Subscription is handle
     * directly into Activity
     */
    public Observable<GithubUser> doActionGithubUser() {
        return mDataManager.getGithubUsers().filter(githubUser -> githubUser != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

}
