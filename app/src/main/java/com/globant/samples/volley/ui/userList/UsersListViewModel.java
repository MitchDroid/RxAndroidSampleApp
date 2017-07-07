package com.globant.samples.volley.ui.userList;

import com.globant.samples.volley.data.SchedulerHelper;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.repository.UserRepository;
import com.globant.samples.volley.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public class UsersListViewModel extends BasePresenter<UserListView> {

    private final UserRepository mUserRepository;

    private final SchedulerHelper mScheduler;


    @Inject
    public UsersListViewModel(UserRepository mUserRepository, SchedulerHelper scheduler) {
        this.mUserRepository = mUserRepository;
        this.mScheduler = scheduler;

    }


    /**
     * This method implements Retrofit Library using RxAndroid
     * The Composite Subscription is handle
     * directly into Activity
     */
    public Observable<List<Item>> doActionGithubUser() {
        return mUserRepository.getUsers().filter(items -> items != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(mScheduler.getmScheduler())
                .unsubscribeOn(mScheduler.getmScheduler());
    }

}
