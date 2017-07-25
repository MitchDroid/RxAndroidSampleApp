package com.globant.samples.volley.ui.userDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.globant.samples.volley.R;
import com.globant.samples.volley.data.model.item.Item;
import com.globant.samples.volley.ui.userDetails.UserDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.globant.samples.volley.ui.userList.RecyclerViewItemCountAssertion.withItemCount;

/**
 * Created by miller.barrera on 25/07/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GithubUserDetailActivityTest {

    @Rule
    public ActivityTestRule<UserDetailActivity> mActivityTestRule =
            new ActivityTestRule<UserDetailActivity>(UserDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {

                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user_item", mockedItem());
                    Intent result = new Intent(targetContext, UserDetailActivity.class);
                    result.putExtras(bundle);
                    return result;

                }
            };

    @Test
    public void githubUserListSizeActivityTest() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recycler_view_repos_list))
                .check(withItemCount(30));
    }

    @Test
    public void userDetailUserNameActivityTest() {
        onView(withId(R.id.tv_github_user_name)).check(matches(withText("mojombo")));
    }

    @Test
    public void userDetailActionBackButtonActivityTest() {
        onView(withId(R.id.action_bar)).perform(click());
    }


    public Item mockedItem() {
        return new Item("mojombo", 1, "https://avatars3.githubusercontent.com/u/1?v=3"
                , "", "https://api.github.com/users/mojombo", "https://github.com/mojombo"
                , "https://api.github.com/users/mojombo/followers", "https://api.github.com/users/mojombo/following{/other_user}"
                , "https://api.github.com/users/mojombo/gists{/gist_id}", "https://api.github.com/users/mojombo/starred{/owner}{/repo}"
                , "https://api.github.com/users/mojombo/subscriptions", "https://api.github.com/users/mojombo/orgs"
                , "https://api.github.com/users/mojombo/repos", "https://api.github.com/users/mojombo/events{/privacy}"
                , "https://api.github.com/users/mojombo/received_events", "User", false, 48.912292);

    }
}
