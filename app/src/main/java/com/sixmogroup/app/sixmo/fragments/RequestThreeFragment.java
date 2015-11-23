package com.sixmogroup.app.sixmo.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_request_three, container, false);
        next=(TextView)rootView.findViewById(R.id.nextThree);
        fromTime=(TextView)rootView.findViewById(R.id.fromTime);
        tillTime=(TextView)rootView.findViewById(R.id.tillTime);
        RequestEventActivity activity= (RequestEventActivity) getActivity();

        if(activity.getFromHour()!=0 && activity.getFromMin()!=0)
            fromTime.setText(CommonUtils.convertTime24to12(activity.getFromHour(), activity.getFromMin()));
        if(activity.getTillHour()!=0 && activity.getTillMin()!=0)
            tillTime.setText(CommonUtils.convertTime24to12(activity.getTillHour(), activity.getTillMin()));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromTime.getText().toString().equals("00:00")){
                    Snackbar.make(rootView, "Please set starting time", Snackbar.LENGTH_LONG).show();
                }else if(tillTime.getText().toString().equals("00:00")){
                    Snackbar.make(rootView, "Please set ending time", Snackbar.LENGTH_LONG).show();
                }else {
                    RequestEventActivity activity = (RequestEventActivity) getActivity();
                    activity.setEventTime(fromTime.getText().toString() + " to " + tillTime.getText().toString());
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment, new RequestFourFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
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
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                if(activity.getFromHour()!=0 && activity.getFromMin()!=0) {
                    int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                    if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                        timePicker.setHour(activity.getFromHour());
                        timePicker.setMinute(activity.getFromMin());
                    }else {
                        timePicker.setCurrentHour(activity.getFromHour());
                        timePicker.setCurrentMinute(activity.getFromMin());
                    }
                }
                Button okayButton = (Button) dialog.findViewById(R.id.setTime);
                // if decline button is clicked, close the custom dialog
                okayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        int hour = 00;
                        int min = 00;

                        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                        if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                            hour = timePicker.getHour();
                            min = timePicker.getMinute();
                        } else {
                            hour = timePicker.getCurrentHour();
                            min = timePicker.getCurrentMinute();
                        }
                        String minText;
                        if (min < 10) {
                            minText = "0" + min;
                        } else {
                            minText = "" + min;
                        }
                        fromTime.setText(CommonUtils.convertTime24to12(hour, min));
                        RequestEventActivity activity = (RequestEventActivity) getActivity();
                        activity.setFromHour(hour);
                        activity.setFromMin(min);
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
                    RequestEventActivity activity= (RequestEventActivity) getActivity();
                if(activity.getTillHour()!=0 && activity.getTillMin()!=0) {
                    int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                    if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                        timePicker.setHour(activity.getTillHour());
                        timePicker.setMinute(activity.getTillMin());
                    }else{
                        timePicker.setCurrentHour(activity.getTillHour());
                        timePicker.setCurrentMinute(activity.getTillMin());
                    }
                }
                Button okayButton = (Button) dialog.findViewById(R.id.setTime);
                // if decline button is clicked, close the custom dialog
                okayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        int hour = 00;
                        int min = 00;

                        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                        if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                            hour = timePicker.getHour();
                            min = timePicker.getMinute();
                        } else {
                            hour = timePicker.getCurrentHour();
                            min = timePicker.getCurrentMinute();
                        }
                        String minText;
                        if (min < 10) {
                            minText = "0" + min;
                        } else {
                            minText = "" + min;
                        }
                        tillTime.setText(CommonUtils.convertTime24to12(hour, min));
                        RequestEventActivity activity = (RequestEventActivity) getActivity();
                        activity.setTillHour(hour);
                        activity.setTillMin(min);
                        dialog.dismiss();
                    }
                });

            }
        });
        return  rootView;
    }
}
