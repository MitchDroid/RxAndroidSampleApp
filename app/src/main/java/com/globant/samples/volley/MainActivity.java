package com.globant.samples.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.globant.samples.volley.adapters.GitHubUsersAdapter;
import com.globant.samples.volley.model.GithubUser;
import com.globant.samples.volley.model.Item;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    String url = "https://api.github.com/search/users?q=tom+repos:%3E42+followers:%3E50";

    // temporary string to show the parsed response
    private String jsonResponse;

    private GitHubUsersAdapter mAdapter;


    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new GitHubUsersAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchJSONResponse();
    }

    private void fetchJSONResponse() {
        // Pass second argument as "null" for GET requests

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JsonParser parser = new JsonParser();
                JsonElement mJson = parser.parse(response.toString());
                Gson gson = new Gson();
                GithubUser object = gson.fromJson(mJson, GithubUser.class);

                populateAdapter(object.getItems());
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    public void populateAdapter(List<Item> item) {
        mAdapter.populateUsersList(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationController.getInstance() != null) {
            ApplicationController.getInstance().cancelPendingRequests(this.getLocalClassName());
        }
    }
}
