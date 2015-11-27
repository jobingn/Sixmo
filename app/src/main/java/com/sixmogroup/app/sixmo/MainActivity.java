package com.sixmogroup.app.sixmo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.sixmogroup.app.sixmo.adapter.EventListAdapter;
import com.sixmogroup.app.sixmo.model.EventModel;
import com.sixmogroup.app.sixmo.utils.CommonUtils;
import com.sixmogroup.app.sixmo.utils.UserSessionManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    List<EventModel> eventModels= new ArrayList<>();;
    RecyclerView recyclerView;
    EventListAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton requestEventButton;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setSubtitle("Beta Version");
        CommonUtils.checkInternetConnection(this);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnFail(R.drawable.default_banner)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        requestEventButton=(FloatingActionButton)findViewById(R.id.fab);
        requestEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent requestIntent = new Intent(getApplicationContext(), RequestEventActivity.class);
                startActivity(requestIntent);
            }
        });
        /** Webservice call  */
        populateList(this);
    }
    public void populateList(final Activity activity){
        AsyncHttpClient client=new AsyncHttpClient();
        eventModels.clear();
        RequestParams params=new RequestParams();
        client.post(CommonUtils.baseUrl+"allEvents", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();i++) {
                    JSONObject object= null;
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
                super.onFailure(statusCode, headers, responseString, throwable);

            }
        });
    }

    @Override
    public void onRefresh() {
        populateList(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_my_event_requests) {
            Intent intent=new Intent(getApplicationContext(),MyRequestsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_info) {
            Intent intent=new Intent(getApplicationContext(),InfoActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_logout) {
            UserSessionManager sessionManager=new UserSessionManager(getApplicationContext());
            sessionManager.logoutUser();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
