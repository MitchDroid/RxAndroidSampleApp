package com.globant.samples.volley.ui.userList;

import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public class UsersListPresenter extends BasePresenter<UserListView> {

    private final DataManager mDataManager;

    @Inject
    public UsersListPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(UserListView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /**
     * This method implements Retrofit Library using RxAndroid
     * The Composite Subscription is handle
     * directly into Activity
     */
    public Subscription doActionGithubUser() {
        checkViewAttached();
        return mDataManager.getGithubUsers().filter(githubUser -> githubUser != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(githubUser -> {
                    if (githubUser != null) {
                        Timber.d("GITHUB USERS SIZE %s ", githubUser.getItems().size());
                        getMvpView().getGithubUsers(githubUser.getItems());
                    }

                }, throwable -> getMvpView().showError(throwable.getMessage(), ApiConstants.LOW_ERROR));
    }

}
