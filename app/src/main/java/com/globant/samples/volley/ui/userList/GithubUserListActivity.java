package com.globant.samples.volley.ui.userList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.globant.samples.volley.ApplicationController;
import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.ui.base.BaseActivity;
import com.globant.samples.volley.ui.userDetails.UserDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class GithubUserListActivity extends BaseActivity implements UserListView {


    CompositeSubscription mCompositeSubscription;

    @Inject
    UsersListPresenter mUsersListPresenter;

    @Inject
    GitHubUsersListAdapter mGitHubUsersListAdapter;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mGitHubUsersListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUsersListPresenter.attachView(this);
        
        mGitHubUsersListAdapter.setOnItemClickListener((view, position) -> {
            Timber.d("Position %s ", position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user_item", mGitHubUsersListAdapter.get(position));

            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtras(bundle);

            callActivity(intent);
        });

        attachCompositeSubscription();
        fetchJSONRetrofitResponse();
    }


    @Deprecated
    private void fetchJSONVolleyResponse() {
        mUsersListPresenter.getGithubUsers();
    }

    /**
     * Get response from Github API service in
     * order to be handle and subscribe to the Activity
     */
    private void fetchJSONRetrofitResponse() {
        mCompositeSubscription.add(mUsersListPresenter.doActionGithubUser());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationController.getInstance() != null) {
            ApplicationController.getInstance().cancelPendingRequests(this.getLocalClassName());
        }
    }

    public void attachCompositeSubscription() {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUsersListPresenter.detachView();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }

    }


    @Override
    public void getGithubUsers(List<Item> items) {
        mGitHubUsersListAdapter.populateUsersList(items);
    }

    @Override
    public void showError(String message, @ApiConstants.ErrorType int errorType) {
        Timber.d("Github API Service error %s ", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRefresh(boolean show) {
        //TODO Implements progress bar
    }
}
