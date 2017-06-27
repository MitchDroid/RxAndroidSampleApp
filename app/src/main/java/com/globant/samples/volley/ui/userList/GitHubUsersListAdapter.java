package com.globant.samples.volley.ui.userList;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.item.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miller.barrera on 5/06/2017.
 */

public class GitHubUsersListAdapter extends RecyclerView.Adapter<GitHubUsersListAdapter.GitHubUsersAdapterViewHolder> {

    // flag for footer ProgressBar (i.e. last item of list)
    private boolean isLoadingAdded = false;

    private List<Item> mList;
    private Context mContext;
    OnItemClickListener mItemClickListener;
    private ImageView mImageView;

    @Inject
    public GitHubUsersListAdapter(Context context) {
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

    public Item get(int positon) {
        return mList.get(positon);
    }

    public void populateUsersList(List<Item> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mList.size() - 1 && isLoadingAdded) ? 0 : 0;
    }

    public class GitHubUsersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_user_item_view)
        CardView mCardView;

        @BindView(R.id.userImage)
        ImageView image;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.url)
        TextView url;

        public GitHubUsersAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mCardView.setOnClickListener(this);

        }

        public void setUserImage(String url) {
            Picasso.with(mContext).load(url).fit().into(image);
            ViewCompat.setTransitionName(image, "user_image_transition");
        }

        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition(), image);
            }
        }

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ImageView imageView);
    }
}
