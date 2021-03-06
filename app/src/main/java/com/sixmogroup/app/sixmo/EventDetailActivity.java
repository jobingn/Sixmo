package com.sixmogroup.app.sixmo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sixmogroup.app.sixmo.utils.CommonUtils;


public class EventDetailActivity extends AppCompatActivity {
    TextView eventName;
    TextView dateTime;
    TextView venue;
    TextView description;
    TextView eventPrice;
    TextView eventOffers;
    ImageView banner;
    ImageButton call;
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        getSupportActionBar().hide();
        call = (ImageButton) findViewById(R.id.call);
        eventName = (TextView) findViewById(R.id.eventName);
        dateTime = (TextView) findViewById(R.id.eventDateTime);
        venue = (TextView) findViewById(R.id.eventVenue);
        eventPrice = (TextView) findViewById(R.id.eventPrice);
        eventOffers = (TextView) findViewById(R.id.eventOffers);
        description = (TextView) findViewById(R.id.eventDescription);
        banner = (ImageView) findViewById(R.id.imageViewBanner);
        eventName.setText(getIntent().getExtras().getString("name"));
        dateTime.setText(getIntent().getExtras().getString("datetime"));
        venue.setText(getIntent().getExtras().getString("place"));
        eventPrice.setText(getIntent().getExtras().getString("price"));
        eventOffers.setText(getIntent().getExtras().getString("offers"));
        description.setText(getIntent().getExtras().getString("description"));
        imageLoader = ImageLoader.getInstance();
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bannerViewIntent=new Intent(getApplicationContext(), BannerViewActivity.class);
                bannerViewIntent.putExtra("imagepath",getIntent().getExtras().getString("imagepath"));
                startActivity(bannerViewIntent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callPhone = new Intent(Intent.ACTION_CALL);
                callPhone.setData(Uri.parse("tel:" + getIntent().getExtras().getString("mobile")));
                    startActivity(callPhone);
            }
        });
        imageLoader.displayImage(CommonUtils.imageUploadUrl + getIntent().getExtras().getString("imagepath"), banner);

    }
}
