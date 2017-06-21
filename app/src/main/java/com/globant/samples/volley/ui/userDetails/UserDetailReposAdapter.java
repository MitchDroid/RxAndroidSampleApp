package com.globant.samples.volley.ui.userDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.repository.GithubUserRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miller.barrera on 21/06/2017.
 */

public class UserDetailReposAdapter extends RecyclerView.Adapter<UserDetailReposAdapter.UserDetailReposViewHolder> {

    private List<GithubUserRepo> mReposList;
    private Context mContext;

    @Inject
    public UserDetailReposAdapter(Context context) {
        this.mContext = context;
        mReposList = new ArrayList<>();
    }

    @Override
    public UserDetailReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_repos, parent, false);
        return new UserDetailReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserDetailReposViewHolder holder, int position) {
        GithubUserRepo item = mReposList.get(position);

        holder.tvRepoUrl.setText(item.getHtmlUrl());


    }

    @Override
    public int getItemCount() {
        return mReposList.size();
    }


    public void populateReposList(List<GithubUserRepo> reposList) {
        mReposList = reposList;
        notifyDataSetChanged();
    }

    public GithubUserRepo get(int position) {
        return mReposList.get(position);
    }

    public class UserDetailReposViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_url)
        TextView tvRepoUrl;

        public UserDetailReposViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
