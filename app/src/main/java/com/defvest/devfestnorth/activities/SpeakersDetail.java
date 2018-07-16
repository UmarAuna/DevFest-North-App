package com.defvest.devfestnorth.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class SpeakersDetail extends AppCompatActivity {
    TextView FullName,FullTwitter,FullWork,FullTopic,FullAbout;
    String small_name,small_twitter,small_work,small_topic,small_about;
    ImageView Hoto, closed;
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


    }
}
