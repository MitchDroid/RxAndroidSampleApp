package com.globant.samples.volley.ui.presenter;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.globant.samples.volley.ApplicationController;
import com.globant.samples.volley.data.model.GithubUser;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.ui.view.UserView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public class UserPresenter extends BasePresenter<UserView> {

    CompositeSubscription mCompositeSubscription;


    @Inject
    public UserPresenter() {
    }

    @Override
    public void attachView(UserView mvpView) {
        super.attachView(mvpView);

        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

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

}
