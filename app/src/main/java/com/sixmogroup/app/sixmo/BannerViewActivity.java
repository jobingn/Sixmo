package com.sixmogroup.app.sixmo;

import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sixmogroup.app.sixmo.utils.CommonUtils;
import com.sixmogroup.app.sixmo.view.TouchImageView;

public class BannerViewActivity extends AppCompatActivity {
    TouchImageView touchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        touchImageView=(TouchImageView)findViewById(R.id.imageViewBanner);
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(CommonUtils.imageUploadUrl + getIntent().getExtras().getString("imagepath"), touchImageView);
        touchImageView.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                RectF rect = touchImageView.getZoomedRect();
                float currentZoom = touchImageView.getCurrentZoom();
                boolean isZoomed = touchImageView.isZoomed();
                //Log.e("sfsdfdsf", ""+currentZoom+","+isZoomed);
                //Do whater ever stuff u want
            }
        });
    }
}
