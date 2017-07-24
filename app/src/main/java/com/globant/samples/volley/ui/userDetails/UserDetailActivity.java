package com.globant.samples.volley.ui.userDetails;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albinmathew.transitions.ActivityTransition;
import com.albinmathew.transitions.ExitActivityTransition;
import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class UserDetailActivity extends BaseActivity {

    private Item mItem;
    CompositeSubscription mCompositeSubscription;

    @Inject
    UserDetailViewModel mUserDetailViewModel;

    @Inject
    UserDetailReposAdapter mUserDetailReposAdapter;

    @BindView(R.id.user_image)
    ImageView mImage;

    @BindView(R.id.tv_github_user_name)
    TextView mUserName;

    @BindView(R.id.tv_github_user_url)
    TextView mUserUrl;

    @BindView(R.id.tv_github_user_repositories)
    TextView mUserRepositories;

    @BindView(R.id.recycler_view_user_list)
    RecyclerView mRecyclerView;

    private ExitActivityTransition exitTransition;

    private static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "image_transition_name";

    private static final String USER_ITEM_KEY = "user_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        supportPostponeEnterTransition();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("User details");
        }


        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            mItem = b.getParcelable(USER_ITEM_KEY);


            mUserName.setText(mItem.getLogin());
            mUserUrl.setText(mItem.getUrl());
            mUserRepositories.setText(mItem.getReposUrl());

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                String imageTransitionName = b.getString(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
//                mImage.setTransitionName(imageTransitionName);
//            }

            exitTransition = ActivityTransition.with(getIntent()).to(mImage).start(savedInstanceState);
            setImage(mItem.getAvatarUrl());

        }


        mRecyclerView.setAdapter(mUserDetailReposAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        attachCompositeSubscription();
        fetchJSONRetrofitResponse();
    }


    /**
     * Get response from Github API service in
     * order to be handle and subscribe to the Activity
     */
    private void fetchJSONRetrofitResponse() {
        mCompositeSubscription.add(mUserDetailViewModel.doActionGithubUserRepos(mUserName.getText().toString()).subscribe(githubUserRepos -> {
            if (githubUserRepos != null) {
                Timber.d("GITHUB USERS SIZE %s ", githubUserRepos.size());
                populateRepostList(githubUserRepos);
            }

        }, throwable -> showError(throwable.getMessage(), ApiConstants.LOW_ERROR)));
    }


    public void populateRepostList(List<GithubUserRepo> listRepos) {
        mUserDetailReposAdapter.populateReposList(listRepos);
    }

    public void setImage(String url) {
        Picasso.with(this).load(url).fit().into(mImage);
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
    public void onBackPressed() {
        exitTransition.exit(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            exitTransition.exit(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showError(String message, @ApiConstants.ErrorType int errorType) {
        Timber.d("Github API Service error %s ", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
