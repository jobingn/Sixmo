package com.sixmogroup.app.sixmo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;


public class RequestOneFragment extends Fragment {
    TextView next;
    EditText name;
    String bannerPath;
    EditText place;
    ImageView banner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_one, container, false);
        name=(EditText)rootView.findViewById(R.id.name);
        place=(EditText)rootView.findViewById(R.id.place);
        banner=(ImageView)rootView.findViewById(R.id.imageViewBanner);
        next=(TextView)rootView.findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                activity.setName(name.getText().toString());
                activity.setBannerPath(bannerPath);
                activity.setPlace(place.getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new RequestTwoFragment()).commit();
            }
        });

        return  rootView;
    }
}
