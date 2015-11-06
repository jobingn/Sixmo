package com.sixmogroup.app.sixmo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.sixmogroup.app.sixmo.fragments.RequestOneFragment;


public class RequestEventActivity extends AppCompatActivity {
    String name;
    String eventdate;
    String place;
    String startTime;
    String endTime;
    String perStag;
    String othersPrice;
    String description;
    String bannerPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
}