package com.globant.samples.volley.ui.view;


import com.globant.samples.volley.data.remote.ApiConstants;

/**
 * Created by miller.barrera
 */
public interface MvpView {

    /**
     * Show the error event.
     */
    void showError(String message, @ApiConstants.ErrorType int errorType);

    /**
     * Show loader component
     *
     * @param show : show if it is true.
     */
    void showRefresh(boolean show);

}
