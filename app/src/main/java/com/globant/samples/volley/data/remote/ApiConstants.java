package com.globant.samples.volley.data.remote;

import android.support.annotation.IntDef;

/**
 * Created by miller.barrera on 11/11/2016.
 */

public class ApiConstants {

    public static final int LOW_ERROR = 111;

    @IntDef({LOW_ERROR})
    public @interface ErrorType {
    }

    //API URL
    public static final String BASE_URL = "https://api.github.com/search/users?q=tom+repos:%3E42+followers:%3E50";

}
