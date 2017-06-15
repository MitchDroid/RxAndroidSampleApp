package com.globant.samples.volley.data.repository;

import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.remote.DataManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miller.barrera on 13/06/2017.
 */

@Singleton
public class UserRepository {

    private DataManager mDataManager;

    @Inject
    public UserRepository(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    /**
     * Get user from  Realm database.
     **/
    public Observable<List<Item>> getUsers() {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            // Load all persons and merge them with their latest stats from GitHub (if they have any)
            return realm.where(Item.class).isNotNull("login")
                    .findAllSorted("login").asObservable().flatMap(items -> {
                        if (!items.isEmpty()) {
                            return Observable.just(realm.copyFromRealm(items));

                        } else {
                            return getUserFromServer();
                        }
                    }).doOnUnsubscribe(() -> realm.close());
        });

    }


    /**
     * Retrive data from server and save it using Realm
     *
     * @return Observable list items
     */
    private Observable<List<Item>> getUserFromServer() {
        Realm realm = Realm.getDefaultInstance();
        return mDataManager.getGithubUsers().filter(githubUser -> githubUser != null)
                .map(githubUser -> githubUser.getItems())
                .doOnNext(items -> realm.executeTransaction(realm1 -> {
                    realm1.insertOrUpdate(items);
                    realm1.close();

                }));
    }
}
