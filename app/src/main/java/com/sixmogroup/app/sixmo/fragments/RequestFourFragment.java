package com.sixmogroup.app.sixmo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;
import com.sixmogroup.app.sixmo.utils.CommonUtils;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class RequestFourFragment extends Fragment {
    TextView requestEvent;
    EditText perStag;
    EditText othersPrice;
    EditText description;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_four, container, false);
        perStag=(EditText)rootView.findViewById(R.id.perStag);
        othersPrice=(EditText)rootView.findViewById(R.id.othersPrice);
        description=(EditText)rootView.findViewById(R.id.name);
        requestEvent=(TextView)rootView.findViewById(R.id.requestEvent);
        requestEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                activity.setPerStag(perStag.getText().toString());
                activity.setOthersPrice(othersPrice.getText().toString());
                activity.setDescription(description.getText().toString());
                AsyncHttpClient client=new AsyncHttpClient();
                RequestParams params=new RequestParams();
                client.post(CommonUtils.baseUrl+"requestEvent", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new RequestDoneFragment()).commit();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(getActivity(),"Sending Request Failure",Toast.LENGTH_LONG);
                    }
                });
            }
        });
        return  rootView;
    }
}
