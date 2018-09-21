package com.defvest.devfestnorth.activities;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstanceIDService";

    @SuppressLint("LongLogTag")
    @Override
    public void onTokenRefresh() {
        //Get uddated token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token: "+ refreshedToken);
        //You can save the token into third party server to do anything you want
    }
}
