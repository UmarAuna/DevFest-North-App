package com.defvest.devfestnorth.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.adapters.CopyURLBroadcast;
import com.defvest.devfestnorth.adapters.Speaker_Adapter;
import com.defvest.devfestnorth.fragments.Feeds;
import com.defvest.devfestnorth.fragments.Organizers;
import com.defvest.devfestnorth.fragments.Schedules;
import com.defvest.devfestnorth.fragments.Venue;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfrag = null;
            switch (item.getItemId()) {
                case R.id.navigation_schedule:
                    selectedfrag = Schedules.newInstance();
                    break;
                case R.id.navigation_feeds:
                    selectedfrag = Feeds.newInstance();
                    break;
                case R.id.navigation_map:
                    selectedfrag = Venue.newInstance();
                    break;
                case R.id.navigation_organizers:
                    selectedfrag = Organizers.newInstance();
                    break;
            }
            FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame,selectedfrag);
            transaction.commit();
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,Schedules.newInstance());
        transaction.commit();

        if (isNetworkConnected() || isWifiConnected()) {
            /*Toast.makeText(this, "Network is Available", Toast.LENGTH_SHORT).show();*/
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setCancelable(false)
                    .setMessage("It looks like your Internet Connection is off. Please turn it " +
                            "on or some features might not work")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(R.drawable.warning).show();
        }

    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            startActivity(new Intent(getApplicationContext(), Speakers.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
        }else if(id== R.id.website){
            OpeninCustomTab("https://ncdevfest.com");
            return true;
        }else if(id == R.id.about){
            startActivity(new Intent(MainActivity.this,About.class));
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void OpeninCustomTab(String url){
        Uri website;
        if(!url.contains("https://") && !url.contains("http://")){
            website = Uri.parse("http://"+url);
        }else{
            website = Uri.parse(url);
        }

        CustomTabsIntent.Builder customtabIntent = new CustomTabsIntent.Builder();
        customtabIntent.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        customtabIntent.setShowTitle(true);
        customtabIntent.addDefaultShareMenuItem();
        customtabIntent.setStartAnimations(this,R.anim.left_in, R.anim.left_out);
        customtabIntent.setExitAnimations(this,R.anim.right_in, R.anim.right_out);
        Intent copyIntent = new Intent(getApplicationContext(),CopyURLBroadcast.class);
        PendingIntent copypendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0, copyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        customtabIntent.addMenuItem("Copy Link",copypendingIntent);

        if(chromeInstalled()){
            customtabIntent.build().intent.setPackage("com.android.chrome");
        }
        customtabIntent.build().launchUrl(MainActivity.this,website);
    }
    private boolean chromeInstalled(){
        try{
            getPackageManager().getPackageInfo("com.android.chrome",0);
            return true;
        }catch (Exception e){
            return false;

        }
    }

}
