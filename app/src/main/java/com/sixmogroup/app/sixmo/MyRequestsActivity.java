package com.sixmogroup.app.sixmo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sixmogroup.app.sixmo.adapter.EventListAdapter;
import com.sixmogroup.app.sixmo.adapter.RequestListAdapter;
import com.sixmogroup.app.sixmo.model.EventModel;
import com.sixmogroup.app.sixmo.utils.CommonUtils;
import com.sixmogroup.app.sixmo.utils.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MyRequestsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    List<EventModel> eventModels = new ArrayList<>();
    RecyclerView recyclerView;
    EventListAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton requestEventButton;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
        getSupportActionBar().hide();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        requestEventButton = (FloatingActionButton)findViewById(R.id.fab);
        requestEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RequestEventActivity.class);
                startActivity(intent);
            }
        });
        /** Webservice call */
        eventModels.clear();
        populateList(this);
    }
    public void populateList(final Activity activity) {
        AsyncHttpClient client = new AsyncHttpClient();
        eventModels.clear();
        RequestParams params=new RequestParams();
        UserSessionManager sessionManager=new UserSessionManager(getApplicationContext());
        params.add("userid", sessionManager.getUserId());
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading events..");
        progressDialog.show();
        client.get(CommonUtils.baseUrl+"getUserRequests", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if(response.getString("response").equals("failed"))
                    Toast.makeText(getApplicationContext(),"No Events to display",Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("My Requests", response.toString());
                swipeRefreshLayout.setRefreshing(false);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = null;
                        try {
                            object = response.getJSONObject(i);
                            EventModel model = new EventModel();
                            model.setEventid(object.getString("eventid"));
                            model.setName(object.getString("name"));
                            model.setEventDate(object.getString("eventdate"));
                            model.setTime(object.getString("starttime"));
                            model.setVenue(object.getString("place"));
                            model.setImagePath(object.getString("imagepath"));
                            model.setOrganizerId(object.getString("organizerid"));
                            EventModel model1 = new EventModel();
                            eventModels.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new EventListAdapter(activity, eventModels);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                super.onFailure(statusCode, headers, responseString, throwable);

            }
        });
    }

    @Override
    public void onRefresh() {
        populateList(this);
    }

}
