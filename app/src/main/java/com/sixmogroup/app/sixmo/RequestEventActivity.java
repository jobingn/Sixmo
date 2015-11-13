package com.sixmogroup.app.sixmo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sixmogroup.app.sixmo.fragments.RequestFourFragment;
import com.sixmogroup.app.sixmo.fragments.RequestOneFragment;
import com.sixmogroup.app.sixmo.fragments.RequestThreeFragment;
import com.sixmogroup.app.sixmo.fragments.RequestTwoFragment;

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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RequestOneFragment fragmentOne = new RequestOneFragment();
        ft.replace(R.id.fragment, fragmentOne);
        ft.addToBackStack(null);
        ft.commit();
    }
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount()-1;
        Log.d("Backstack count",""+count);
    Fragment fragment=null;
    switch (count){
        case 0: {
            this.finish();
           break;}
        case 1: fragment=new RequestOneFragment(); break;
        case 2: fragment=new RequestTwoFragment(); break;
        case 3: fragment=new RequestThreeFragment(); break;
        case 4: fragment=new RequestFourFragment(); break;
    }
        if(count!=0) {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment, fragment);
            //ft.addToBackStack(null);
            ft.commit();
        }
    }
}