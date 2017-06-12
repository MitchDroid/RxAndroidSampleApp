package com.globant.samples.volley.ui.userList;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.globant.samples.volley.ApplicationController;
import com.globant.samples.volley.data.model.user.GithubUser;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.ui.base.BasePresenter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    /*Unused
    * This method implements Volley Library
    * */
    public void getGithubUsers() {
        checkViewAttached();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiConstants.BASE_URL, null, response -> {

            JsonParser parser = new JsonParser();
            JsonElement mJson = parser.parse(response.toString());
            Gson gson = new Gson();
            GithubUser object = gson.fromJson(mJson, GithubUser.class);

            getMvpView().getGithubUsers(object.getItems());
            Timber.d("List User size $s, ", object.getItems().size());


        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            getMvpView().showError(error.getMessage(), ApiConstants.LOW_ERROR);
            Timber.e("Error");
        });

        /* Add your Requests to the RequestQueue to execute */
        ApplicationController.getInstance().addToRequestQueue(req);
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
