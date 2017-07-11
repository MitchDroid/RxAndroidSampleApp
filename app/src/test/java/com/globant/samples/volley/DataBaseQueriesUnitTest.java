package com.globant.samples.volley;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.remote.database.AppDatabase;
import com.globant.samples.volley.data.remote.database.DataBaseQueries;
import com.globant.samples.volley.data.remote.database.DatabaseCreator;
import com.globant.samples.volley.data.remote.database.GithubUserDao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by miller.barrera on 10/07/2017.
 */

public class DataBaseQueriesUnitTest {

    DataBaseQueries mDataBaseQueries;
    DatabaseCreator mDatabaseCreator;
    AppDatabase mAppDatabase;


    @Before
    public void setUp() {

        mDatabaseCreator = Mockito.mock(DatabaseCreator.class);
        mAppDatabase = Mockito.mock(AppDatabase.class);

        doNothing().when(mDatabaseCreator).createDb();
        mDatabaseCreator.createDb();

        when(mAppDatabase.userDao()).thenReturn(userDao());

        mDataBaseQueries = new DataBaseQueries(mDatabaseCreator);

    }


    @Test
    public void getDataBase() throws Exception {

        Mockito.verify(mDatabaseCreator, times(1)).createDb();
//        mDataBaseQueries.insertData(mockedList());
//
//        List<GithubUserRepo> theList = mDataBaseQueries.getAllUserReposList();
//
//        assertThat(theList.size(), is(4));

    }

    public List<GithubUserRepo> mockedList() {
        List<GithubUserRepo> mockedList = new ArrayList<>();
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
        mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));

        return mockedList;

    }

    public GithubUserDao userDao() {
        return new GithubUserDao() {
            @Override
            public List<GithubUserRepo> getAll() {
                List<GithubUserRepo> mockedList = new ArrayList<>();
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));

                return mockedList;
            }

            @Override
            public List<GithubUserRepo> getByUserName(String userName) {
                List<GithubUserRepo> mockedList = new ArrayList<>();
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));
                mockedList.add(new GithubUserRepo("mojombo", 17358646, "https://github.com/mojombo/30daysoflaptops.github.io"));

                return mockedList;

            }

            @Override
            public void insertAll(List<GithubUserRepo> products) {

            }

            @Override
            public void delete(GithubUserRepo user) {

            }
        };
    }

}
