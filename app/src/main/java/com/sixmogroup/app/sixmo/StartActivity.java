package com.sixmogroup.app.sixmo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.utils.UserSessionManager;


public class StartActivity extends AppCompatActivity {
    TextView getStarted;

    @Override
    protected void onStart() {
        super.onStart();
    }
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
        getSupportActionBar().hide();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground( final Void ... params ) {
                // tasks to be completed before starting app
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute( final Void result ) {
                UserSessionManager sessionManager=new UserSessionManager(getApplicationContext());
                if(sessionManager.isUserLoggedIn()){
                    Intent eventsIntent;
                    if(sessionManager.getUserRole().equals("user")) {
                        eventsIntent = new Intent(getApplicationContext(), MainActivity.class);
                    }else{
                        eventsIntent = new Intent(getApplicationContext(), AdminMainActivity.class);
                    }
                    startActivity(eventsIntent);
                    finish();
                }else {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        }.execute();

    }

}
