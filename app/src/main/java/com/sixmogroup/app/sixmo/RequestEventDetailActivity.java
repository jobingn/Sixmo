package com.sixmogroup.app.sixmo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sixmogroup.app.sixmo.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RequestEventDetailActivity extends AppCompatActivity {
    TextView eventName;
    TextView dateTime;
    TextView venue;
    TextView description;
    TextView accept;
    TextView decline;
    ImageView banner;
    String eventid;
    ImageButton call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_event_detail);
        getSupportActionBar().hide();
        call = (ImageButton) findViewById(R.id.call);
        eventName = (TextView) findViewById(R.id.eventName);
        dateTime = (TextView) findViewById(R.id.eventDateTime);
        venue = (TextView) findViewById(R.id.eventVenue);
        description = (TextView) findViewById(R.id.eventDescription);
        accept = (TextView) findViewById(R.id.textViewAccept);
        decline= (TextView) findViewById(R.id.textViewDecline);
        banner = (ImageView) findViewById(R.id.imageViewBanner);
        System.out.println(eventid);
        eventName.setText(getIntent().getExtras().getString("name"));
        dateTime.setText(getIntent().getExtras().getString("datetime"));
        venue.setText(getIntent().getExtras().getString("place"));
        description.setText(getIntent().getExtras().getString("description"));
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callPhone = new Intent(Intent.ACTION_CALL);
                callPhone.setData(Uri.parse("tel:" + getIntent().getExtras().getString("mobile")));
                startActivity(callPhone);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("eventid", getIntent().getExtras().getString("eventid"));
                client.get(CommonUtils.baseUrl + "acceptRequest", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getString("response").equals("success"))
                                Toast.makeText(getApplicationContext(), "Accepted Request", Toast.LENGTH_LONG).show();

                            else
                                Toast.makeText(getApplicationContext(), "Failed to accept request", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(getApplicationContext(), "Failed to accept request", Toast.LENGTH_LONG).show();
                    }
                });
                Intent adminIntent=new Intent(getApplicationContext(),AdminMainActivity.class);
                startActivity(adminIntent);
                finish();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client=new AsyncHttpClient();
                RequestParams params=new RequestParams();
                params.add("eventid", getIntent().getExtras().getString("eventid"));
                client.get(CommonUtils.baseUrl + "declineRequest", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getString("response").equals("success"))
                                Toast.makeText(getApplicationContext(), "Declined Request", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Failed to decline request", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(getApplicationContext(), "Failed to decline request", Toast.LENGTH_LONG).show();
                    }
                });
                Intent adminIntent=new Intent(getApplicationContext(),AdminMainActivity.class);
                startActivity(adminIntent);
                finish();
            }
        });
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(CommonUtils.imageUploadUrl + getIntent().getExtras().getString("imagepath"), banner);


    }
}
