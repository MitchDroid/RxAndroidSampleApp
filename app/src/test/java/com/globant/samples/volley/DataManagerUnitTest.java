package com.globant.samples.volley;

import com.globant.samples.volley.data.model.user.GithubUser;
import com.globant.samples.volley.data.remote.DataManager;
import com.globant.samples.volley.data.remote.UserApiService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.junit.Assert.assertNotNull;
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

        when(mUserApiService.doGetUsers()).thenReturn(Observable.from(mockedList()));

        mDataManager = new DataManager(mUserApiService);
    }

    @Test
    public void getGithubUser() throws Exception {

        assertNotNull(mDataManager.getGithubUsers());
        Mockito.verify(mUserApiService, times(1)).doGetUsers();

    }

    public List<GithubUser> mockedList() {
        List<GithubUser> mockedList = new ArrayList<>();
        mockedList.add(new GithubUser(93, false, null));
        mockedList.add(new GithubUser(93, false, null));
        mockedList.add(new GithubUser(93, false, null));
        mockedList.add(new GithubUser(93, false, null));
        return mockedList;

    }
}
