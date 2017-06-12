package com.globant.samples.volley.ui.base;


/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 * Created by presenter.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}