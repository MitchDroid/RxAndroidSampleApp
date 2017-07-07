package com.globant.samples.volley;

import com.globant.samples.volley.data.SchedulerHelper;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.data.repository.UserRepository;
import com.globant.samples.volley.ui.userList.UsersListViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.observers.TestSubscriber;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by miller.barrera on 29/06/2017.
 */


public class UserListViewModelsUnitTest {

    UsersListViewModel mUsersListViewModel;

    UserRepository mUserRepository;

    SchedulerHelper mSchedulerHelper;

    @Before
    public void setUp() {

        // Override RxJava schedulers
        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());

        RxJavaHooks.setOnComputationScheduler(scheduler -> Schedulers.immediate());

        RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());

        // Override RxAndroid schedulers
        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
        mUserRepository = Mockito.mock(UserRepository.class);
        mSchedulerHelper = Mockito.mock(SchedulerHelper.class);
        when(mUserRepository.getUsers())
                .thenReturn(Observable.just(mockedList()));
        when(mSchedulerHelper.getmScheduler()).thenReturn(rxAndroidPlugins.getSchedulersHook().getMainThreadScheduler());

        mUsersListViewModel = new UsersListViewModel(mUserRepository, mSchedulerHelper);
    }

    @Test
    public void readUserList() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        mUsersListViewModel.doActionGithubUser().subscribe(testSubscriber);
        //asserts
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(mockedList());
        //verifications
        Mockito.verify(mUserRepository, times(1)).getUsers();

    }


    public List<Item> mockedList() {
        List<Item> mockedList = new ArrayList<>();
        mockedList.add(new Item("mojombo", 1, "https://avatars3.githubusercontent.com/u/1?v=3"
                , "", "https://api.github.com/users/mojombo", "https://github.com/mojombo"
                , "https://api.github.com/users/mojombo/followers", "https://api.github.com/users/mojombo/following{/other_user}"
                , "https://api.github.com/users/mojombo/gists{/gist_id}", "https://api.github.com/users/mojombo/starred{/owner}{/repo}"
                , "https://api.github.com/users/mojombo/subscriptions", "https://api.github.com/users/mojombo/orgs"
                , "https://api.github.com/users/mojombo/repos", "https://api.github.com/users/mojombo/events{/privacy}"
                , "https://api.github.com/users/mojombo/received_events", "User", false, 48.912292));
        mockedList.add(new Item("mojombo", 1, "https://avatars3.githubusercontent.com/u/1?v=3"
                , "", "https://api.github.com/users/mojombo", "https://github.com/mojombo"
                , "https://api.github.com/users/mojombo/followers", "https://api.github.com/users/mojombo/following{/other_user}"
                , "https://api.github.com/users/mojombo/gists{/gist_id}", "https://api.github.com/users/mojombo/starred{/owner}{/repo}"
                , "https://api.github.com/users/mojombo/subscriptions", "https://api.github.com/users/mojombo/orgs"
                , "https://api.github.com/users/mojombo/repos", "https://api.github.com/users/mojombo/events{/privacy}"
                , "https://api.github.com/users/mojombo/received_events", "User", false, 48.912292));
        mockedList.add(new Item("mojombo", 1, "https://avatars3.githubusercontent.com/u/1?v=3"
                , "", "https://api.github.com/users/mojombo", "https://github.com/mojombo"
                , "https://api.github.com/users/mojombo/followers", "https://api.github.com/users/mojombo/following{/other_user}"
                , "https://api.github.com/users/mojombo/gists{/gist_id}", "https://api.github.com/users/mojombo/starred{/owner}{/repo}"
                , "https://api.github.com/users/mojombo/subscriptions", "https://api.github.com/users/mojombo/orgs"
                , "https://api.github.com/users/mojombo/repos", "https://api.github.com/users/mojombo/events{/privacy}"
                , "https://api.github.com/users/mojombo/received_events", "User", false, 48.912292));
        mockedList.add(new Item("mojombo", 1, "https://avatars3.githubusercontent.com/u/1?v=3"
                , "", "https://api.github.com/users/mojombo", "https://github.com/mojombo"
                , "https://api.github.com/users/mojombo/followers", "https://api.github.com/users/mojombo/following{/other_user}"
                , "https://api.github.com/users/mojombo/gists{/gist_id}", "https://api.github.com/users/mojombo/starred{/owner}{/repo}"
                , "https://api.github.com/users/mojombo/subscriptions", "https://api.github.com/users/mojombo/orgs"
                , "https://api.github.com/users/mojombo/repos", "https://api.github.com/users/mojombo/events{/privacy}"
                , "https://api.github.com/users/mojombo/received_events", "User", false, 48.912292));
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
