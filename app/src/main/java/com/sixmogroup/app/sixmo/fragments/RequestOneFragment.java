package com.sixmogroup.app.sixmo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.RequestEventActivity;


public class RequestOneFragment extends Fragment {
    TextView next;
    EditText name;
    String bannerPath;
    String bannerFileName;
    EditText place;
    ImageView banner;
    ImageView editImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_one, container, false);
        name=(EditText)rootView.findViewById(R.id.name);
        place=(EditText)rootView.findViewById(R.id.place);
        banner=(ImageView)rootView.findViewById(R.id.imageViewBanner);
        editImage=(ImageView)rootView.findViewById(R.id.imageViewEdit);
        next=(TextView)rootView.findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestEventActivity activity= (RequestEventActivity) getActivity();
                activity.setName(name.getText().toString());
                activity.setBannerPath(bannerPath);
                activity.setBannerFileName(bannerFileName);
                activity.setPlace(place.getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new RequestTwoFragment()).commit();
            }
        });
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,1);
            }
        });
        return  rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                bannerPath = cursor.getString(columnIndex);
                cursor.close();
                banner.setImageBitmap(BitmapFactory.decodeFile(bannerPath));
                // Get the Image's file name
                String fileNameSegments[] = bannerPath.split("/");
                bannerFileName = fileNameSegments[fileNameSegments.length - 1];
            } else {
                Toast.makeText(getActivity(), "Please Pick Image", Toast.LENGTH_LONG).show();
            }
        }
    }
}
