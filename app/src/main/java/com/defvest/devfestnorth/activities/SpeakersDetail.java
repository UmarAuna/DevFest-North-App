package com.defvest.devfestnorth.activities;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.adapters.CopyURLBroadcast;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpeakersDetail extends AppCompatActivity {
    TextView FullName,FullTwitter,FullWork,FullTopic,FullAbout;
    String small_name,small_twitter,small_work,small_topic,small_about;
    CircleImageView Hoto;
    ImageView closed;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers_detail);
        this.setFinishOnTouchOutside(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; //w
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; //h
        window.setAttributes(layoutParams);

        FullName = findViewById(R.id.SDname);
        FullTwitter = findViewById(R.id.SDtwitter);
        FullWork = findViewById(R.id.SDwork);
        FullTopic = findViewById(R.id.SDtopic);
        FullAbout = findViewById(R.id.SDabout);
        Hoto = findViewById(R.id.SDimage);
        closed = findViewById(R.id.SDclose);

        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Intent get = getIntent();
        Bundle display = get.getExtras();
        if (display != null) {
            small_name = display.getString("Name");
            small_twitter = display.getString("Twitter");
            small_topic = display.getString("Topic");
            small_work = display.getString("Work");
            small_about = display.getString("About");

            String s = getIntent().getStringExtra("Photo");

            RequestOptions placeholderRequest = new RequestOptions();
            placeholderRequest.placeholder(R.drawable.warning);

            Glide.with(getApplicationContext()).setDefaultRequestOptions(placeholderRequest).load(s).into(Hoto);

            FullName.setText(small_name);
            FullTwitter.setText(small_twitter);
            FullWork.setText(small_work);
            FullAbout.setText(small_about);
            FullTopic.setText(small_topic);
        }

        FullTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpeninCustomTab(small_twitter);
            }
        });
    }

    public void OpeninCustomTab(String url){
        Uri website;
        if(!url.contains("https://") && !url.contains("http://")){
            website = Uri.parse("https://"+"www.twitter.com/"+url);
        }else{
            website = Uri.parse("www.twitter.com"+url);
        }

        CustomTabsIntent.Builder customtabIntent = new CustomTabsIntent.Builder();
        customtabIntent.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        customtabIntent.setShowTitle(true);
        customtabIntent.addDefaultShareMenuItem();
        /*customtabIntent.setStartAnimations(this, R.anim.left_in, R.anim.left_out);
        customtabIntent.setExitAnimations(this, R.anim.right_in, R.anim.right_out);*/
        Intent copyIntent = new Intent(this,CopyURLBroadcast.class);
        PendingIntent copypendingIntent = PendingIntent.getBroadcast(this,0, copyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        customtabIntent.addMenuItem("Copy Link",copypendingIntent);

        if(chromeInstalled()){
            customtabIntent.build().intent.setPackage("com.android.chrome");
        }
        customtabIntent.build().launchUrl(this,website);
    }

    private boolean chromeInstalled(){
        try{
            getPackageManager().getPackageInfo("com.android.chrome",0);
            return true;
        }catch (Exception e){
            return false;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
