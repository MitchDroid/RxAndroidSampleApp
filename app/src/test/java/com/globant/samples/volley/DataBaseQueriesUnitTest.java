package com.globant.samples.volley;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.remote.database.AppDatabase;
import com.globant.samples.volley.data.remote.database.DataBaseQueries;
import com.globant.samples.volley.data.remote.database.DatabaseCreator;
import com.globant.samples.volley.data.remote.database.GithubUserDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by miller.barrera on 10/07/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DataBaseQueriesUnitTest {

    DataBaseQueries mDataBaseQueries;
    DatabaseCreator mDatabaseCreator;
    AppDatabase mAppDatabase;
    GithubUserDao mGithubUserDao;
    private Context mContext;


    @Before
    public void setUp() {
        mContext = RuntimeEnvironment.application;

        mDatabaseCreator = Mockito.mock(DatabaseCreator.class);
        mGithubUserDao = Mockito.mock(GithubUserDao.class);
        mAppDatabase = Mockito.mock(AppDatabase.class);


        mAppDatabase = Room.databaseBuilder(mContext,
                AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        when(mDatabaseCreator.getDatabase()).thenReturn(mAppDatabase);
        doNothing().when(mGithubUserDao).insertAll(mockedList());
        when(mGithubUserDao.getByUserName(anyString())).thenReturn(mockedList());

        mDataBaseQueries = new DataBaseQueries(mDatabaseCreator);
    }


    @Test
    public void tesReposByUserName() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        mDataBaseQueries.insertData(mockedList());
        List<GithubUserRepo> theList = mDataBaseQueries.getReposListByUserName("mojombo");
        assertThat(theList.size(), is(4));
    }

    @After
    public void closeDatabase(){
        mDataBaseQueries.closeDatabase();
    }


    public List<GithubUserRepo> mockedList() {
        List<GithubUserRepo> mockedList = new ArrayList<>();
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358647, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358648, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358649, "https://github.com/mojombo/30daysoflaptops.github.io"));

        return mockedList;
    }

}
