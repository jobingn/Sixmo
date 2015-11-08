package com.sixmogroup.app.sixmo.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;
import com.sixmogroup.app.sixmo.utils.CommonUtils;
import com.sixmogroup.app.sixmo.utils.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class RequestFourFragment extends Fragment {
    TextView requestEvent;
    EditText perStag;
    EditText othersPrice;
    EditText description;
    String encodedString;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_four, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Requesting Event..");
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
                encodeImagetoString();
            }
        });
        return  rootView;
    }


    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {
                progressDialog.show();
            };

            @Override
            protected String doInBackground(Void... params) {
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                Bitmap bitmap;
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(activity.getBannerPath(),options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                uploadImageToServer();
            }
        }.execute(null, null, null);
    }

    public void uploadImageToServer() {
        RequestEventActivity activity= (RequestEventActivity) getActivity();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("image", encodedString);
        params.put("filename", activity.getBannerFileName());
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post(CommonUtils.imageServiceUrl+"eventBannerUpload",
                params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("image Upload Service :",responseBody.toString());
                        requestUserEvent();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getActivity(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getActivity(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getActivity(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
    public void requestUserEvent() {
        RequestEventActivity activity= (RequestEventActivity) getActivity();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("name", activity.getName());
        params.put("place",activity.getPlace());
        params.put("eventdate",activity.getEventdate());
        params.put("perstag",activity.getPerStag());
        params.put("otherprice",activity.getOthersPrice());
        params.put("time",activity.getEventTime());
        params.put("description",activity.getDescription());
        UserSessionManager sessionManager=new UserSessionManager(getActivity());
        if(sessionManager.getUserRole().equals("admin"))
        params.put("status",1);
        else
        params.put("status",0);
        params.put("organizer",sessionManager.getUserId());
        params.put("imagepath",activity.getBannerFileName());
        client.get(CommonUtils.baseUrl+"requestEvent", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(" Request WS Success", response.toString());
                try {
                    if (response.getString("response").equals("success")) {
                        progressDialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new RequestDoneFragment()).commit();
                    } else {
                        Toast.makeText(getActivity(), "Event request failure", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d(" Request WS", responseString.toString());
                Toast.makeText(getActivity(), "Event request failure", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}
