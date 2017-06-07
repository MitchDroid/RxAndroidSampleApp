package com.globant.samples.volley.ui.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.Item;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
            mItem = b.getParcelable("user_item");

            setImage(mItem.getAvatarUrl());
            mUserName.setText(mItem.getLogin());
            mUserUrl.setText(mItem.getUrl());
            mUserRepositories.setText(mItem.getReposUrl());

        }
    }

    public void setImage(String url){
        Picasso.with(this).load(url).fit().into(mImage);
    }
}
