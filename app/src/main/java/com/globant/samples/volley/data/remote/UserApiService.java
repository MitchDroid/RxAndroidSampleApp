package com.globant.samples.volley.data.remote;

import com.globant.samples.volley.data.model.repository.GithubUserRepo;
import com.globant.samples.volley.data.model.user.GithubUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by miller.barrera.
 */

public interface UserApiService {

    @GET(ApiConstants.BASE_URL_USERS)
    Observable<GithubUser> doGetUsers();

    @GET(ApiConstants.BASE_URL_REPOS + "{github_user_name}/repos")
    Observable<List<GithubUserRepo>> doGetUserRepos(@Path("github_user_name") String userName);

//    class Factory {
//        public static UserApiService create() {
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//            Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
//            return retrofit.create(UserApiService.class);
//        }
//    }
}
