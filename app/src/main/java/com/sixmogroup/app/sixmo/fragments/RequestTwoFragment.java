package com.sixmogroup.app.sixmo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;
import com.sixmogroup.app.sixmo.utils.CommonUtils;

import java.util.Calendar;

public class RequestTwoFragment extends Fragment {
    TextView next;
    DatePicker datePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_two, container, false);
        datePicker=(DatePicker)rootView.findViewById(R.id.datePicker);
        next=(TextView)rootView.findViewById(R.id.nextTwo);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        RequestEventActivity activity= (RequestEventActivity) getActivity();
        if (activity.getDay() != 0 && activity.getMonth()!=0 && activity.getYear()!=0){
            datePicker.updateDate(activity.getYear(), activity.getMonth(), activity.getDay());
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                activity.setDay(datePicker.getDayOfMonth());
                activity.setMonth(datePicker.getMonth()+1);
                activity.setYear(datePicker.getYear());
                activity.setDate(CommonUtils.getSqlDate(datePicker.getDayOfMonth(),datePicker.getMonth()+1,datePicker.getYear()));
                activity.setEventdate(CommonUtils.formatDate(datePicker.getDayOfMonth(),datePicker.getMonth()+1,datePicker.getYear()));
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.fragment, new RequestThreeFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return  rootView;
    }
}
