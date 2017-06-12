package com.globant.samples.volley.ui.userList;

import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.ui.base.MvpView;

import java.util.List;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public interface UserListView extends MvpView {
    void getGithubUsers(List<Item> items);
}
