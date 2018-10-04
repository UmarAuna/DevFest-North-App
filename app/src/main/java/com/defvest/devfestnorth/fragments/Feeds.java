package com.defvest.devfestnorth.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.models.feeds_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feeds extends Fragment {
    private FirebaseRecyclerAdapter<feeds_model, FeedsViewHolder> firebaseRecyclerAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference myref;
    View rootView;
    TextView  mEmptyListView;


    public static Feeds newInstance(){
        Feeds fragment = new Feeds();
        return fragment;
    }
    public Feeds() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feeds, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_feeds);
        mEmptyListView = rootView.findViewById(R.id.list_feeds_error);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        myref = FirebaseDatabase.getInstance().getReference().child("Post");
        myref.keepSynced(true);

        if (isNetworkConnected() || isWifiConnected()) {
            /*Toast.makeText(this, "Network is Available", Toast.LENGTH_SHORT).show();*/
        } else {
            mEmptyListView.setVisibility(View.VISIBLE);
        }

        FirebaseRecyclerOptions<feeds_model> options = new FirebaseRecyclerOptions.Builder<feeds_model>().setQuery(myref,feeds_model.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<feeds_model, FeedsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FeedsViewHolder holder, int position, @NonNull feeds_model model) {
                holder.setTitle(model.getTitle());
                holder.setContent(model.getContent());
                holder.setTime(model.getTime());

            }

            @NonNull
            @Override
            public FeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_item,parent,false);
                return new FeedsViewHolder(v);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter );
        return rootView;
    }

    private class FeedsViewHolder extends RecyclerView.ViewHolder {
        View mview;
        TextView Title, Content, Time;

        public FeedsViewHolder(View itemView){
            super(itemView);
            mview = itemView;
            Title = itemView.findViewById(R.id.ftitle);
            Content = itemView.findViewById(R.id.fcontent);
            Time = itemView.findViewById(R.id.ftime);
        }

        public void setTitle(String title) {
            Title.setText(title);
        }

        public void setContent(String content) {
            Content.setText(content);
        }

        public void setTime(String time) {
            Time.setText(time);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }
}


