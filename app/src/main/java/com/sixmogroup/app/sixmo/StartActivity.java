package com.sixmogroup.app.sixmo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.utils.UserSessionManager;


public class StartActivity extends AppCompatActivity {
    TextView getStarted;

    @Override
    protected void onStart() {
        super.onStart();
        UserSessionManager sessionManager=new UserSessionManager(getApplicationContext());
        if(sessionManager.isUserLoggedIn()){
            Intent eventsIntent;
            if(sessionManager.getUserRole().equals("user")) {
                eventsIntent = new Intent(getApplicationContext(), MainActivity.class);
            }else{
                eventsIntent = new Intent(getApplicationContext(), AdminMainActivity.class);
            }
            startActivity(eventsIntent);
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        getStarted=(TextView)findViewById(R.id.buttonGetStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }

}
