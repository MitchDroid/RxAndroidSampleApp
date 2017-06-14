package com.globant.samples.volley.ui.userDetails;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailActivity extends BaseActivity {

    private Item mItem;

    @BindView(R.id.user_image)
    ImageView mImage;

    @BindView(R.id.tv_github_user_name)
    TextView mUserName;

    @BindView(R.id.tv_github_user_url)
    TextView mUserUrl;

    @BindView(R.id.tv_github_user_repositories)
    TextView mUserRepositories;

    private static final String USER_ITEM_KEY = "user_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("User details");
        }

        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            mItem = b.getParcelable(USER_ITEM_KEY);

            setImage(mItem.getAvatarUrl());
            mUserName.setText(mItem.getLogin());
            mUserUrl.setText(mItem.getUrl());
            mUserRepositories.setText(mItem.getReposUrl());

        }
    }

    public void setImage(String url) {
        Picasso.with(this).load(url).fit().into(mImage);
    }
}
