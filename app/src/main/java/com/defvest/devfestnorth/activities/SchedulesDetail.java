package com.defvest.devfestnorth.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
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


public class SchedulesDetail extends AppCompatActivity {
    TextView Full_Title,Full_Time,Full_When,Full_Where,Full_Speaker,Full_Category;
    String small_title,small_time,small_when,small_where,small_spaker,small_category;
    CircleImageView photo;
    ImageView  closed;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedules_detail);
       this.setFinishOnTouchOutside(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; //w
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; //h
        window.setAttributes(layoutParams);

        Full_Title = findViewById(R.id.atitle);
        Full_Time = findViewById(R.id.atime);
        Full_When = findViewById(R.id.awhen);
        Full_Where = findViewById(R.id.awhere);
        Full_Speaker = findViewById(R.id.aspeaker);
        Full_Category = findViewById(R.id.acategory);
        photo = findViewById(R.id.aprofile_photo);
        closed = findViewById(R.id.sclose);

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
            small_title = display.getString("title");
            small_time = display.getString("time");
            small_when = display.getString("when");
            small_where = display.getString("where");
            small_spaker = display.getString("speaker");
            small_category = display.getString("uiux_category");

            String s = getIntent().getStringExtra("image");

            RequestOptions placeholderRequest = new RequestOptions();
            placeholderRequest.placeholder(R.drawable.warning);

            Glide.with(getApplicationContext()).setDefaultRequestOptions(placeholderRequest).load(s).into(photo);

            Full_Title.setText(small_title);
            Full_Time.setText(small_time);
            Full_When.setText(small_when);
            Full_Where.setText(small_where);
            Full_Speaker.setText(small_spaker);
            Full_Category.setText(small_category);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
