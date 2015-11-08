package com.sixmogroup.app.sixmo.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;
import com.sixmogroup.app.sixmo.utils.CommonUtils;

public class RequestThreeFragment extends Fragment {
    TextView fromTime;
    TextView tillTime;
    TextView next;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_three, container, false);
        next=(TextView)rootView.findViewById(R.id.nextThree);
        fromTime=(TextView)rootView.findViewById(R.id.fromTime);
        tillTime=(TextView)rootView.findViewById(R.id.tillTime);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                activity.setEventTime(fromTime.getText().toString()+" to "+tillTime.getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new RequestFourFragment()).commit();
            }
        });
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // code for setting from time
                final Dialog dialog = new Dialog(getActivity(),R.style.dialogStyle);
                // Include dialog.xml file
                dialog.setContentView(R.layout.select_time_dialog);
                // Set dialog title
                dialog.setTitle("Start Time");
                // set values for custom dialog components - text, image and button
                final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                dialog.show();

                Button okayButton = (Button) dialog.findViewById(R.id.setTime);
                // if decline button is clicked, close the custom dialog
                okayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        int hour = 00;
                        int min = 00;

                        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                        if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1){
                            hour = timePicker.getHour();
                            min = timePicker.getMinute();
                        } else {
                            hour = timePicker.getCurrentHour();
                            min = timePicker.getCurrentMinute();
                        }
                        String minText;
                        if(min<10){
                            minText="0"+min;
                        }else{
                            minText=""+min;
                        }
                        fromTime.setText(CommonUtils.convertTime24to12(hour,min));
                        dialog.dismiss();
                    }
                });

            }
        });
        tillTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code for setting till time
                final Dialog dialog = new Dialog(getActivity(),R.style.dialogStyle);
                // Include dialog.xml file
                dialog.setContentView(R.layout.select_time_dialog);
                // Set dialog title
                dialog.setTitle("End Time");
                dialog.setCanceledOnTouchOutside(true);
                // set values for custom dialog components - text, image and button
                final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                dialog.show();

                Button okayButton = (Button) dialog.findViewById(R.id.setTime);
                // if decline button is clicked, close the custom dialog
                okayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        int hour = 00;
                        int min = 00;

                        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                        if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1){
                            hour = timePicker.getHour();
                            min = timePicker.getMinute();
                        } else {
                            hour = timePicker.getCurrentHour();
                            min = timePicker.getCurrentMinute();
                        }
                        String minText;
                        if(min<10){
                            minText="0"+min;
                        }else{
                            minText=""+min;
                        }
                        tillTime.setText(CommonUtils.convertTime24to12(hour,min));
                        dialog.dismiss();
                    }
                });

            }
        });
        return  rootView;
    }
}
