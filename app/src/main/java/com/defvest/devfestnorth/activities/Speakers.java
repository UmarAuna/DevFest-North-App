package com.defvest.devfestnorth.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.adapters.Speaker_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Speakers extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    ArrayList<String> NameList;
    ArrayList<String> TopicList;
    ArrayList<String> WorkList;
    ArrayList<String> ProfilePicList;

    ArrayList<String> AboutList;
    ArrayList<String> TwitterList;

    Speaker_Adapter searchAdapter;
    ImageView Back,Clear;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Back = findViewById(R.id.imgback);
        Clear = findViewById(R.id.imgclear);
        error = findViewById(R.id.error);

        Clear.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadData();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        if (isNetworkConnected() || isWifiConnected()) {

            //TODO: Remove Toast
            Toast.makeText(this, "Network is Available", Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setCancelable(false)
                    .setMessage("It looks like your internet connection is off. Please turn it " +
                            "on or some features might not work")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(R.drawable.warning).show();
        }


        /*
         * Create a array list for each node you want to use
         * */
        NameList = new ArrayList<>();
        TopicList = new ArrayList<>();
        WorkList = new ArrayList<>();
        ProfilePicList = new ArrayList<>();

        AboutList = new ArrayList<>();
        TwitterList = new ArrayList<>();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());

                    Clear.setVisibility(View.VISIBLE);

                    Clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            search_edit_text.setText("");
                            loadData();

                            Clear.setVisibility(View.INVISIBLE);
                            error.setVisibility(View.GONE);

                        }
                    });
                } else {
                    /*
                     * Clear the list when editText is empty
                     * */
                    loadData();
                    Clear.setVisibility(View.INVISIBLE);
                    error.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("Speakers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                NameList.clear();
                TopicList.clear();
                WorkList.clear();
                ProfilePicList.clear();

                AboutList.clear();
                TwitterList.clear();

                int counter = 0;

                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //String uid = snapshot.getKey();
                    String About = snapshot.child("About").getValue(String.class);
                    String Name = snapshot.child("Name").getValue(String.class);
                    String Photo = snapshot.child("Photo").getValue(String.class);
                    String Topic = snapshot.child("Topic").getValue(String.class);
                    String Twitter = snapshot.child("Twitter").getValue(String.class);
                    String Work = snapshot.child("Work").getValue(String.class);


                    if (Name != null) {
                        if (Name.toLowerCase().contains(searchedString.toLowerCase())) {
                            AboutList.add(About);//About
                            NameList.add(Photo);//Photo
                            ProfilePicList.add(Work);//Work
                            TopicList.add(Name);//Name
                            TwitterList.add(Topic);//Topic
                            WorkList.add(Twitter);//Twitter

                            counter++;
                        } else if (Work != null && Twitter != null && (Twitter.toLowerCase().contains(searchedString.toLowerCase()) || Work.toLowerCase().contains(searchedString.toLowerCase()))) {
                            AboutList.add(About);//About
                            NameList.add(Photo);//Photo
                            ProfilePicList.add(Work);//Work
                            TopicList.add(Name);//Name
                            TwitterList.add(Topic);//Topic
                            WorkList.add(Twitter);//Twitter

                            counter++;
                        }
                    }

                    /*
                     * Get maximum of 20 searched results only
                     * */
                    if (counter == 90)
                        break;

                    if (counter == 0){
                       error.setVisibility(View.VISIBLE);
                    }else{
                        error.setVisibility(View.GONE);
                    }
                }

                searchAdapter = new Speaker_Adapter(Speakers.this, NameList,TopicList,WorkList, ProfilePicList,AboutList, TwitterList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Speakers.this, "Unable to find data "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadData(){
        databaseReference.child("Speakers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                NameList.clear();
                TopicList.clear();
                WorkList.clear();
                ProfilePicList.clear();
                AboutList.clear();
                TwitterList.clear();
                //recyclerView.removeAllViews();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //String uid = snapshot.getKey();
                    String About = snapshot.child("About").getValue(String.class);
                    String Name = snapshot.child("Name").getValue(String.class);
                    String Photo = snapshot.child("Photo").getValue(String.class);
                    String Topic = snapshot.child("Topic").getValue(String.class);
                    String Twitter = snapshot.child("Twitter").getValue(String.class);
                    String Work = snapshot.child("Work").getValue(String.class);

                    AboutList.add(About);//About
                    NameList.add(Photo);//Photo
                    ProfilePicList.add(Work);//Work
                    TopicList.add(Name);//Name
                    TwitterList.add(Topic);//Topic
                    WorkList.add(Twitter);//Twitter

                }

                searchAdapter = new Speaker_Adapter(Speakers.this, NameList,TopicList,WorkList, ProfilePicList,AboutList, TwitterList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Speakers.this, "Unable to find data "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onStart() {
        super.onStart();
        loadData();
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        loadData();
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
