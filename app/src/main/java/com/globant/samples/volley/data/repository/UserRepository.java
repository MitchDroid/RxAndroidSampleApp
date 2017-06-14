package com.globant.samples.volley.data.repository;

import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.remote.ApiConstants;
import com.globant.samples.volley.data.remote.DataManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by miller.barrera on 13/06/2017.
 */

@Singleton
public class UserRepository {

    private Subscription mSubscription;
    private DataManager mDataManager;

    @Inject
    public UserRepository(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    /**
     * Get the default instance from Realm database.
     **/
    public Realm getRealmDefaultInstance() {
        return Realm.getDefaultInstance();
    }

    /**
     * Get user from  Realm database.
     **/
    public Observable<RealmResults<Item>> getUser() {
        // Load all persons and merge them with their latest stats from GitHub (if they have any)
        return getRealmDefaultInstance().where(Item.class).isNotNull("login")
                .findAllSorted("login").asObservable().subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread()).filter(items -> items.isLoaded());
    }

    private void getUserFromServer() {
        mDataManager.getGithubUsers().filter(githubUser -> githubUser != null)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(githubUser -> {
                    if (githubUser != null) {
                        Timber.d("GITHUB USERS SIZE %s ", githubUser.getItems().size());

                    }

                }, throwable -> {

                });
    }
}
