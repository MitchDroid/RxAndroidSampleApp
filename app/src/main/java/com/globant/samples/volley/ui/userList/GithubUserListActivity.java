package com.globant.samples.volley.ui.userList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.albinmathew.transitions.ActivityTransitionLauncher;
import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.ui.base.BaseActivity;
import com.globant.samples.volley.ui.userDetails.UserDetailActivity;
import com.globant.samples.volley.utils.EndlessRecyclerViewScrollListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class GithubUserListActivity extends BaseActivity implements UserListView {


    private static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "image_transition_name";
    CompositeSubscription mCompositeSubscription;

    @Inject
    UsersListViewModel mUsersListViewModel;

    @Inject
    GitHubUsersListAdapter mGitHubUsersListAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    private LinearLayoutManager mLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;

    private static final String USER_ITEM_KEY = "user_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setAdapter(mGitHubUsersListAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };

        mRecyclerView.addOnScrollListener(mScrollListener);// Adds the scroll listener to RecyclerView


        mGitHubUsersListAdapter.setOnItemClickListener((view, position, imageView) -> {
            Timber.d("Position %s ", position);
            Bundle bundle = new Bundle();

            bundle.putParcelable(USER_ITEM_KEY, mGitHubUsersListAdapter.get(position));

            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtras(bundle);

            ActivityTransitionLauncher.with(GithubUserListActivity.this).from(imageView).launch(intent);
//            callActivity(intent);
        });
        attachCompositeSubscription();
        fetchJSONRetrofitResponse();

    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }


    /**
     * Get response from Github API service in
     * order to be handle and subscribe to the Activity
     */
    private void fetchJSONRetrofitResponse() {
        mCompositeSubscription.add(mUsersListViewModel.doActionGithubUser().subscribe(items -> {
            if (items != null) {
                Timber.d("GITHUB USERS SIZE %s ", items.size());
                getGithubUsers(items);
            }

        }, throwable -> showError(throwable.getMessage(), ApiConstants.LOW_ERROR)));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void attachCompositeSubscription() {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
