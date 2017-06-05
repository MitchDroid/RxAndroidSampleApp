package com.globant.samples.volley.adapters;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.samples.volley.R;
import com.globant.samples.volley.model.GithubUser;
import com.globant.samples.volley.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miller.barrera on 5/06/2017.
 */

public class GitHubUsersAdapter extends RecyclerView.Adapter<GitHubUsersAdapter.GitHubUsersAdapterViewHolder> {

    private List<Item> mList;
    private Context mContext;

    public GitHubUsersAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public GitHubUsersAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new GitHubUsersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GitHubUsersAdapterViewHolder holder, int position) {
        Item user = mList.get(position);

        holder.setUserImage(user.getAvatarUrl());
        holder.name.setText(user.getLogin());
        holder.url.setText(user.getUrl());


    }

    public void populateUsersList(List<Item> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GitHubUsersAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        TextView name;

        TextView url;

        public GitHubUsersAdapterViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            url = (TextView) itemView.findViewById(R.id.url);

        }

        public void setUserImage(String url) {
            Picasso.with(mContext).load(url).fit().into(image);
        }
    }
}
