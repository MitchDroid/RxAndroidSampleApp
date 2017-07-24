package com.globant.samples.volley.ui.userList;


import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.globant.samples.volley.R;
import com.globant.samples.volley.ui.userDetails.UserDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.globant.samples.volley.ui.userList.RecyclerViewItemCountAssertion.withItemCount;


@RunWith(AndroidJUnit4.class)
public class GithubUserListActivityTest {

    @Rule
    public IntentsTestRule<GithubUserListActivity> mActivityTestRule
            = new IntentsTestRule<GithubUserListActivity>(GithubUserListActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            return super.getActivityIntent();
        }
    };


    @Test
    public void githubUserListClickActionsActivityTest() {
        onView(withId(R.id.recycler_view_user_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        intended(hasComponent(UserDetailActivity.class.getName()));
    }

    @Test
    public void githubUserListSizeActivityTest() {
        onView(withId(R.id.recycler_view_user_list))
                .check(withItemCount(30));
    }

}
