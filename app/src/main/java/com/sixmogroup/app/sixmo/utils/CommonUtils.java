package com.sixmogroup.app.sixmo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.R;

/**
 * Created by Jobin on Oct 24.
 */
public class CommonUtils {
    /** String constants */

    public static String baseUrl="http://supermatrimony.in/sixmo.in/mobile/";
    public static String imageUploadUrl="http://supermatrimony.in/sixmo.in/mobile/images/";

    /** Utility Methods */

    public String generateGcmId(){
        String regId="";
    return regId;
    }
    public boolean checkLoggedIn(){
        return false;
    }
    public void flushAllLocalData(){

    }
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
    public static void checkInternetConnection(final Activity activity){
        if(isConnectedToInternet(activity.getApplicationContext())==false) {
            final Dialog dialog = new Dialog(activity, R.style.dialogStyle);
            // Include dialog.xml file
            dialog.setContentView(R.layout.internet_connection_dialog);
            // Set dialog title
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            TextView okayButton = (TextView) dialog.findViewById(R.id.tryAgain);
            // if decline button is clicked, close the custom dialog
            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    checkInternetConnection(activity);
                }
            });
        }
    }

}
