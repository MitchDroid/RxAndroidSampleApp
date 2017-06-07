package com.globant.samples.volley.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.globant.samples.volley.ApplicationController;
import com.globant.samples.volley.R;
import com.globant.samples.volley.injection.component.ActivityComponent;
import com.globant.samples.volley.injection.component.DaggerActivityComponent;
import com.globant.samples.volley.injection.module.ActivityModule;

/**
 * Created by miller.barrera on 7/06/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getActivityComponent().inject(this);

    }


    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                    .applicationComponent(ApplicationController.get(this).getComponent()).build();
        }
        return mActivityComponent;
    }

    protected void callActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
