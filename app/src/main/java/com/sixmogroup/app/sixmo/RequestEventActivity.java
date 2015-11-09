package com.sixmogroup.app.sixmo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sixmogroup.app.sixmo.fragments.RequestOneFragment;
import com.sixmogroup.app.sixmo.utils.CommonUtils;
import com.sixmogroup.app.sixmo.utils.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;


public class RequestEventActivity extends AppCompatActivity {
    String name;
    String eventdate;
    String place;
    String eventTime;
    String perStag;
    String othersPrice;
    String description;
    String bannerPath;
    String bannerFileName;

    public String getBannerFileName() {
        return bannerFileName;
    }

    public void setBannerFileName(String bannerFileName) {
        this.bannerFileName = bannerFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }


    public void setPlace(String place) {
        this.place = place;
    }
    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }


    public String getPerStag() {
        return perStag;
    }

    public void setPerStag(String perStag) {
        this.perStag = perStag;
    }

    public String getOthersPrice() {
        return othersPrice;
    }

    public void setOthersPrice(String othersPrice) {
        this.othersPrice = othersPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_event);
        getSupportActionBar().hide();
        Fragment fragmentOne=new RequestOneFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragmentOne).commit();
}

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}