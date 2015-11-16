package com.sixmogroup.app.sixmo;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sixmogroup.app.sixmo.fragments.RequestFourFragment;
import com.sixmogroup.app.sixmo.fragments.RequestOneFragment;
import com.sixmogroup.app.sixmo.fragments.RequestThreeFragment;
import com.sixmogroup.app.sixmo.fragments.RequestTwoFragment;

import java.sql.Date;

public class RequestEventActivity extends AppCompatActivity {
    String name;
    String eventdate;
    Date date;
    String place;
    String eventTime;
    String perStag;
    String othersPrice;
    String description;
    String bannerPath;
    String bannerFileName;

    int day;
    int month;
    int year;

    int fromHour;
    int fromMin;
    int tillHour;
    int tillMin;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getFromMin() {
        return fromMin;
    }

    public void setFromMin(int fromMin) {
        this.fromMin = fromMin;
    }

    public int getTillHour() {
        return tillHour;
    }

    public void setTillHour(int tillHour) {
        this.tillHour = tillHour;
    }

    public int getTillMin() {
        return tillMin;
    }

    public void setTillMin(int tillMin) {
        this.tillMin = tillMin;
    }
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RequestEventActivity.this);
            alertDialogBuilder.setTitle("Request Event");
            alertDialogBuilder.setMessage("Are you want to exit ?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                    dialog.cancel();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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