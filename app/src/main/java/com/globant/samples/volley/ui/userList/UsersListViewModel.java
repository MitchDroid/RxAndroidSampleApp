package com.globant.samples.volley.ui.userList;

import android.os.HandlerThread;

import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.repository.UserRepository;
import com.globant.samples.volley.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public class UsersListViewModel extends BasePresenter<UserListView> {

    private final UserRepository mUserRepository;
    private Scheduler scheduler;
    private HandlerThread handlerThread;


    @Inject
    public UsersListViewModel(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
        handlerThread = new HandlerThread("backgroundThread");
        handlerThread.start();
        scheduler = AndroidSchedulers.from(handlerThread.getLooper());
    }


    /**
     * This method implements Retrofit Library using RxAndroid
     * The Composite Subscription is handle
     * directly into Activity
     */
    public Observable<List<Item>> doActionGithubUser() {
        return mUserRepository.getUsers().filter(items -> items != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(scheduler)
                .unsubscribeOn(scheduler);
    }

}
