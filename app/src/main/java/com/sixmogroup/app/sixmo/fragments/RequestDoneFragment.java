package com.sixmogroup.app.sixmo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.MainActivity;
import com.sixmogroup.app.sixmo.R;


public class RequestDoneFragment extends Fragment {
    TextView done;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_done, container, false);
        done=(TextView)rootView.findViewById(R.id.doneContinue);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent=new Intent(getActivity(), MainActivity.class);
                startActivity(mainIntent);
                getActivity().finish();
            }
        });
        return  rootView;
    }

}
