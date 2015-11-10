package com.sixmogroup.app.sixmo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.sixmogroup.app.sixmo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jobin on Oct 24.
 */
public class CommonUtils {
    /** String constants */

    public static String baseUrl="http://supermatrimony.in/sixmo.in/mobile/";
    public static String imageUploadUrl="http://supermatrimony.in/sixmo.in/mobile/images/events/";
    public static String imageServiceUrl="http://supermatrimony.in/sixmo.in/mobile/images/";

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

    public static String convertTime24to12(int hours, int mins){
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "pm";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "am";
        } else if (hours == 12)
            timeSet = "pm";
        else
            timeSet = "am";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        return aTime;
    }
    public static String formatDate(int day, int month,int year){
        String dateFormated="";
        String[] dateMonth={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
        String[] dayWeek={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("dd/M/yyyy").parse(day+"/"+month+"/"+year));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek < 8 & dayOfWeek!=0){
            dateFormated=dateFormated.concat(dayWeek[dayOfWeek-1]+" "+day);
        }
        if(month<13 & month!=0){
            dateFormated=dateFormated.concat(","+dateMonth[month-1]);
        }
        dateFormated=dateFormated.concat(" "+year);
        return dateFormated;
    }
    public static boolean validateEmail(String email){
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    public static boolean validateMobile(String mobile){
        if(mobile == null || mobile.length()!=10) {
            return false;
        } else {
            return true;
        }
    }
}
