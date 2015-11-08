package com.sixmogroup.app.sixmo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sixmogroup.app.sixmo.utils.CommonUtils;
import com.sixmogroup.app.sixmo.utils.UserSessionManager;


import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText mobile;
    TextView siginIn;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        layout=(RelativeLayout)findViewById(R.id.main_layout);
        email=(EditText)findViewById(R.id.email);
        mobile=(EditText)findViewById(R.id.phone);
        siginIn=(TextView)findViewById(R.id.sign_in_button);
        siginIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AsyncHttpClient client=new AsyncHttpClient();
                RequestParams params=new RequestParams();
                params.add("email", email.getText().toString());
                params.add("phone", mobile.getText().toString());
                client.post(CommonUtils.baseUrl + "memberLogin.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("response json",response.toString());
                        try {
                            if(response.getString("response").equals("success")) {
                                String memberid=response.getString("memberid");
                                String username=response.getString("name");
                                String role=response.getString("role");
                                String mobile=response.getString("mobile");
                                String email=response.getString("email");
                                UserSessionManager sessionManager=new UserSessionManager(getApplicationContext());
                                sessionManager.createUserLoginSession(memberid,username,email,mobile,role);
                                Intent eventsIntent;
                                if(role.equals("user")) {
                                    eventsIntent = new Intent(getApplicationContext(), MainActivity.class);
                                }else{
                                    eventsIntent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                }
                                startActivity(eventsIntent);
                                finish();
                            }
                            else{
                                Snackbar.make(layout,"Invalid email or mobile",Snackbar.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("response failure", responseString);
                        Snackbar.make(layout,"Login Failed",Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}

