package com.sixmogroup.app.sixmo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.sixmogroup.app.sixmo.LoginActivity;

import java.util.HashMap;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context mContext;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "SixmoPref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USER_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_IMAGE_PATH = "imagePath";
    public static final String KEY_ADDRESS_1 = "address1";
    public static final String KEY_ADDRESS_2 = "address2";
    public static final String KEY_STATE = "state";
    public static final String KEY_ZIP_CODE = "zipCode";
    public static final String KEY_ROLE = "country";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_USER_ID = "userID";

    public UserSessionManager()
    {

    }
    // Constructor
    public UserSessionManager(Context context){
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public SharedPreferences getSharedPref(){
        return pref;
    }
    /*
     * Returns the preference editor
     */
    public Editor getPrefEditor(){
        return editor;
    }

    //Create login session
    public void createUserLoginSession(String userid,String name, String email,String mobile,String role){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing userid in pref
        editor.putString(KEY_USER_ID, userid);
        // Storing name in pref
        editor.putString(KEY_USER_NAME, name);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        // Storing mobile in pref
        editor.putString(KEY_MOBILE, mobile);
        // Storing user role  in pref
        editor.putString(KEY_ROLE, role);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);

            return true;
        }
        return false;
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name-
        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));
        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        // user email id
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(mContext, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }

    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public String getUserRole() {
        return pref.getString(KEY_ROLE,null);
    }
}
