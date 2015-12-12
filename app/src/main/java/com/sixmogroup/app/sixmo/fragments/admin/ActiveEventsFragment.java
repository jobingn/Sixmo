package com.sixmogroup.app.sixmo.fragments.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;
import com.sixmogroup.app.sixmo.adapter.EventListAdapter;
import com.sixmogroup.app.sixmo.model.EventModel;
import com.sixmogroup.app.sixmo.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ActiveEventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    List<EventModel> eventModels= new ArrayList<>();;
    RecyclerView recyclerView;
    EventListAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton requestEventButton;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.activity_main, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        requestEventButton=(FloatingActionButton)rootView.findViewById(R.id.fab);
        requestEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent requestIntent = new Intent(getActivity(), RequestEventActivity.class);
                startActivity(requestIntent);
            }
        });
        /** Webservice call  */
        populateList(getActivity());
        return  rootView;
    }
    public void populateList(final Activity activity){
        AsyncHttpClient client=new AsyncHttpClient();
        eventModels.clear();
        RequestParams params=new RequestParams();
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading events..");
        progressDialog.show();
        client.post(CommonUtils.baseUrl + "allEvents", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
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
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onRefresh() {
        populateList(getActivity());
    }

}
