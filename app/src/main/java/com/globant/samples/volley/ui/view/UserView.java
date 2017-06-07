package com.globant.samples.volley.ui.view;

import com.globant.samples.volley.data.model.Item;

import java.util.List;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public interface UserView extends MvpView {
    void getGithubUsers(List<Item> items);
}
