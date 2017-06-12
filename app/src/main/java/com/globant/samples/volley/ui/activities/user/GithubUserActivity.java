package com.globant.samples.volley.ui.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.globant.samples.volley.ApplicationController;
import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.ui.activities.details.UserDetailActivity;
import com.globant.samples.volley.ui.activities.base.BaseActivity;
import com.globant.samples.volley.ui.adapters.user.GitHubUsersAdapter;
import com.globant.samples.volley.ui.presenter.user.UserPresenter;
import com.globant.samples.volley.ui.view.user.UserView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class GithubUserActivity extends BaseActivity implements UserView {


    CompositeSubscription mCompositeSubscription;

    @Inject
    UserPresenter mUserPresenter;

    @Inject
    GitHubUsersAdapter mGitHubUsersAdapter;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mGitHubUsersAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

       // mUserPresenter.attachView(this);

        mGitHubUsersAdapter.setOnItemClickListener((view, position) -> {
            Timber.d("Position %s ", position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user_item", mGitHubUsersAdapter.get(position));

            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtras(bundle);

            callActivity(intent);
        });

        //fetchJSONVolleyResponse();
        attachSubscription();
        fetchJSONRetrofitResponse();
    }


    @Deprecated
    private void fetchJSONVolleyResponse() {
        mUserPresenter.getGithubUsers();
    }

    /**
     * Get response from Github API service in
     * order to be handle and subscribe to the Activity
     */
    private void fetchJSONRetrofitResponse() {
        mCompositeSubscription.add(mUserPresenter.doAction().filter(githubUser -> githubUser != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(githubUser -> {
                    if (githubUser != null) {
                        Timber.d("GITHUB USERS SIZE %s ", githubUser.getItems().size());
                        mGitHubUsersAdapter.populateUsersList(githubUser.getItems());
                    }

                }, throwable -> showError(throwable.getMessage(), ApiConstants.LOW_ERROR)));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationController.getInstance() != null) {
            ApplicationController.getInstance().cancelPendingRequests(this.getLocalClassName());
        }
    }

    public void attachSubscription() {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mUserPresenter.detachView();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }

    }


    @Override
    public void getGithubUsers(List<Item> items) {
        mGitHubUsersAdapter.populateUsersList(items);
    }

    @Override
    public void showError(String message, @ApiConstants.ErrorType int errorType) {
        Timber.d("Github API Service error %s ", message);
    }

    @Override
    public void showRefresh(boolean show) {

    }
}
