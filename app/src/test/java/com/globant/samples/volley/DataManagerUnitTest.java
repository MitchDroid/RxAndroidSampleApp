package com.globant.samples.volley;

import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.model.user.GithubUser;
import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.data.remote.UserApiService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by miller.barrera on 10/07/2017.
 */

public class DataManagerUnitTest {

    UserApiService mUserApiService;
    DataManager mDataManager;

    @Before
    public void setUp() {
        mUserApiService = Mockito.mock(UserApiService.class);

        when(mUserApiService.doGetUsers()).thenReturn((Observable.just(mockedUser())));
        when(mUserApiService.doGetUserRepos(anyString())).thenReturn(Observable.just(mockedReposList()));

        mDataManager = new DataManager(mUserApiService);
    }

    @Test
    public void getGithubUser() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        mDataManager.getGithubUsers().subscribe(testSubscriber);
        //asserts
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(mockedUser());

        Mockito.verify(mUserApiService, times(1)).doGetUsers();


    }

    @Test
    public void getGithubRepos() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        mDataManager.getGithubUserRepos(anyString()).subscribe(testSubscriber);
        //asserts
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(mockedReposList());

        Mockito.verify(mUserApiService, times(1)).doGetUserRepos(anyString());
    }

    public GithubUser mockedUser() {
        return new GithubUser(93, false, mockedItemList());
    }

    public List<GithubUserRepo> mockedReposList() {
        List<GithubUserRepo> mockedReposList = new ArrayList<>();
        mockedReposList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedReposList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedReposList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedReposList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));

        return mockedReposList;
    }

    public List<Item> mockedItemList() {
        List<Item> mockedList = new ArrayList<>();
        mockedList.add(new Item("mojombo", 1, "https://avatars3.githubusercontent.com/u/1?v=3"
                , "", "https://api.github.com/users/mojombo", "https://github.com/mojombo"
                , "https://api.github.com/users/mojombo/followers", "https://api.github.com/users/mojombo/following{/other_user}"
                , "https://api.github.com/users/mojombo/gists{/gist_id}", "https://api.github.com/users/mojombo/starred{/owner}{/repo}"
                , "https://api.github.com/users/mojombo/subscriptions", "https://api.github.com/users/mojombo/orgs"
                , "https://api.github.com/users/mojombo/repos", "https://api.github.com/users/mojombo/events{/privacy}"
                , "https://api.github.com/users/mojombo/received_events", "User", false, 48.912292));

        return mockedList;
    }
}
