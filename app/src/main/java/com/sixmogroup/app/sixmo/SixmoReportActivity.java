package com.sixmogroup.app.sixmo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sixmogroup.app.sixmo.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SixmoReportActivity extends AppCompatActivity {
    TextView totalProjects;
    TextView totalMembers;
    TextView activeProjects;
    TextView pendingProjects;
    TextView declinedProjects;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixmo_report);
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        totalProjects=(TextView)findViewById(R.id.totalProjectsCount);
        totalMembers=(TextView)findViewById(R.id.totalMembersCount);
        activeProjects=(TextView)findViewById(R.id.activeProjectsCount);
        pendingProjects=(TextView)findViewById(R.id.pendingRequestsCount);
        declinedProjects=(TextView)findViewById(R.id.declinedRequestsCount);
        AsyncHttpClient client=new AsyncHttpClient();
        progressDialog.show();
        client.get(CommonUtils.baseUrl+"getCurrentReport",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    totalProjects.setText(response.getString("totalprojects"));
                    activeProjects.setText(response.getString("activeprojects"));
                    totalMembers.setText(response.getString("totalmembers"));
                    pendingProjects.setText(response.getString("pendingprojectrequests"));
                    declinedProjects.setText(response.getString("rejectedprojects"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Data request Failure",Toast.LENGTH_LONG).show();
            }
        });

    }
}
