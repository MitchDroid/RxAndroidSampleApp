package com.globant.samples.volley.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.globant.samples.volley.ApplicationController;
import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.Item;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.ui.adapters.GitHubUsersAdapter;
import com.globant.samples.volley.ui.presenter.UserPresenter;
import com.globant.samples.volley.ui.view.UserView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class GithubUserActivity extends BaseActivity implements UserView {

    String url = "https://api.github.com/search/users?q=tom+repos:%3E42+followers:%3E50";

    // temporary string to show the parsed response
    private String jsonResponse;

    private GitHubUsersAdapter mAdapter;

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

        mUserPresenter.attachView(this);

        mGitHubUsersAdapter.setOnItemClickListener((view, position) -> {
            Timber.d("Position %s ", position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user_item", mGitHubUsersAdapter.get(position));

            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtras(bundle);

            callActivity(intent);
        });

        //fetchJSONVolleyResponse();
        fetchJSONRetrofitResponse();
    }

    private void fetchJSONVolleyResponse() {
        mUserPresenter.getGithubUsers();
    }

    private void fetchJSONRetrofitResponse() {
        mUserPresenter.doActionGithubUser();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationController.getInstance() != null) {
            ApplicationController.getInstance().cancelPendingRequests(this.getLocalClassName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserPresenter.detachView();

    }

    @Override
    public void getGithubUsers(List<Item> items) {
        mGitHubUsersAdapter.populateUsersList(items);
    }

    @Override
    public void showError(String message, @ApiConstants.ErrorType int errorType) {

    }

    @Override
    public void showRefresh(boolean show) {

    }
}
