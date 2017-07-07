package com.globant.samples.volley;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.repository.UserReposRepository;
import com.globant.samples.volley.ui.userDetails.UserDetailViewModel;

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
 * Created by miller.barrera on 6/07/2017.
 */

public class UserDetailViewModelUnitTest {

    UserReposRepository mUserReposRepository;
    UserDetailViewModel mUserDetailViewModel;

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
                return Schedulers.io();
            }
        });

        mUserReposRepository = Mockito.mock(UserReposRepository.class);
        when(mUserReposRepository.getRepositories("mojombo")).thenReturn(Observable.just(mockedList()));

        mUserDetailViewModel = new UserDetailViewModel(mUserReposRepository);

    }

    @Test
    public void readRepositoriesList() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        mUserDetailViewModel.doActionGithubUserRepos("mojombo").subscribe(testSubscriber);

        //asserts
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(mockedList());
        //verifications
        Mockito.verify(mUserReposRepository, times(1)).getRepositories("mojombo");

    }


    public List<GithubUserRepo> mockedList() {
        List<GithubUserRepo> mockedList = new ArrayList<>();
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "asteroids", "mojombo/asteroids"
                , null, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "asteroids", "mojombo/asteroids"
                , null, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "asteroids", "mojombo/asteroids"
                , null, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "asteroids", "mojombo/asteroids"
                , null, "https://github.com/mojombo/30daysoflaptops.github.io"));

        return mockedList;

    }
}
