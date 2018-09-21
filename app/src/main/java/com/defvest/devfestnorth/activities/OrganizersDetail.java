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

import de.hdodenhof.circleimageview.CircleImageView;

public class OrganizersDetail extends AppCompatActivity{
    TextView Full_Name,Full_gdg, Full_about;
    String small_Name,small_gdg, small_about;
    CircleImageView photo;
    ImageView closed;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizers_detail);
        this.setFinishOnTouchOutside(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; //w
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; //h
        window.setAttributes(layoutParams);

        Full_Name = findViewById(R.id.dname);
        Full_gdg = findViewById(R.id.dgdg);
        Full_about = findViewById(R.id.dabout);
        photo = findViewById(R.id.dprofile_photo);
        closed = findViewById(R.id.closed);

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
            small_Name = display.getString("name");
            small_gdg = display.getString("gdg");
            small_about = display.getString("about");

            String s = getIntent().getStringExtra("photo");

            RequestOptions placeholderRequest = new RequestOptions();
            placeholderRequest.placeholder(R.drawable.warning);

            Glide.with(getApplicationContext()).setDefaultRequestOptions(placeholderRequest).load(s).into(photo);

            Full_Name.setText(small_Name);
            Full_gdg.setText(small_gdg);
            Full_about.setText(small_about);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
