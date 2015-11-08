package com.sixmogroup.app.sixmo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;
import com.sixmogroup.app.sixmo.utils.CommonUtils;

public class RequestTwoFragment extends Fragment {
    TextView next;
    DatePicker datePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_two, container, false);
        datePicker=(DatePicker)rootView.findViewById(R.id.datePicker);
        next=(TextView)rootView.findViewById(R.id.nextTwo);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                activity.setEventdate(CommonUtils.formatDate(datePicker.getDayOfMonth(),datePicker.getMonth(),datePicker.getYear()));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new RequestThreeFragment()).commit();
            }
        });
        return  rootView;
    }
}
