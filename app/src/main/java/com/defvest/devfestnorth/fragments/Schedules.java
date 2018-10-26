package com.defvest.devfestnorth.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.activities.OrganizersDetail;
import com.defvest.devfestnorth.activities.SchedulesDetail;
import com.defvest.devfestnorth.models.organizers_model;
import com.defvest.devfestnorth.models.schedules_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Schedules extends Fragment {
    View rootView;
    Context context;

    private FirebaseRecyclerAdapter<schedules_model, ScheduleViews> firebaserecyclerAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference myref;
    TextView   mEmptyListView;
    public static Schedules newInstance(){
        Schedules fragment = new Schedules();
        return fragment;
    }
    public Schedules() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_schedules, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_schedules);
        mEmptyListView = rootView.findViewById(R.id.list_schedules_error);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        myref = FirebaseDatabase.getInstance().getReference().child("Agenda");
        myref.keepSynced(true);

        if (isNetworkConnected() || isWifiConnected()) {
            /*Toast.makeText(this, "Network is Available", Toast.LENGTH_SHORT).show();*/
        } else {
            mEmptyListView.setVisibility(View.VISIBLE);
        }

        FirebaseRecyclerOptions<schedules_model> options = new FirebaseRecyclerOptions.Builder<schedules_model>().setQuery(myref,schedules_model.class).build();

        firebaserecyclerAdapter = new FirebaseRecyclerAdapter<schedules_model, ScheduleViews>(options) {


            @SuppressLint("CheckResult")
            @Override
            protected void onBindViewHolder(@NonNull ScheduleViews viewholder, final int position, final schedules_model model) {
                viewholder.setTitle(model.getTitle());
                viewholder.setTime(model.getTime());
                viewholder.setWhen(model.getWhen());
                viewholder.setWhere(model.getWhere());
                viewholder.setSpeaker(model.getSpeaker());
                RequestOptions placeholderRequest = new RequestOptions();
                placeholderRequest.placeholder(R.drawable.warning);
                viewholder.setCategory(model.getCategory());
                viewholder.setImage(model.getImage());
                viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle extras = new Bundle();
                        Intent nextactivity = new Intent(v.getContext(), SchedulesDetail.class);

                        nextactivity.putExtra("title",model.getTitle());
                        nextactivity.putExtra("time",model.getTime());
                        nextactivity.putExtra("when",model.getWhen());
                        nextactivity.putExtra("where",model.getWhere());
                        nextactivity.putExtra("speaker",model.getSpeaker());
                        nextactivity.putExtra("category",model.getCategory());

                        nextactivity.putExtra("image",model.getImage());


                        nextactivity.putExtras(extras);
                        v.getContext().startActivity(nextactivity);

                    }
                });

            }

            @Override
            public ScheduleViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedules_custom_list,parent,false);
                return new ScheduleViews(v);
            }
        };
        recyclerView.setAdapter(firebaserecyclerAdapter);

        return rootView;
    }
    public static class ScheduleViews extends RecyclerView.ViewHolder {
        View mView;
        TextView title;
        TextView time;
        TextView when;
        TextView where;
        TextView speaker;
        TextView category;

        CircleImageView image;


        public ScheduleViews(View itemView) {
            super(itemView);
            mView = itemView;
            title = (TextView) itemView.findViewById(R.id.Stitle);
            time = (TextView) itemView.findViewById(R.id.Stime);
            when = (TextView) itemView.findViewById(R.id.Swhen);
            where = (TextView) itemView.findViewById(R.id.Swhere);
            speaker = (TextView) itemView.findViewById(R.id.Sspeaker);
            category = (TextView) itemView.findViewById(R.id.Scategory);

            image=  itemView.findViewById(R.id.Sphoto);
        }

        public void setTitle(String titles) {
            title.setText(titles);
        }
        public void setTime(String times) {
            time.setText(times);
        }
        public void setWhen(String whens) {
            when.setText(whens);
        }
        public void setWhere(String wheres) {
            where.setText(wheres);
        }
        public void setSpeaker(String speakers) {
            speaker.setText(speakers);
        }
        public void setCategory(String categories) {
            category.setText(categories);
        }

        @SuppressLint("CheckResult")
        public void setImage(String images) {
            RequestOptions placeholderRequest = new RequestOptions();
            placeholderRequest.placeholder(R.drawable.warning);
            Glide.with(mView.getContext()).load(images).into(image);
        }


    }


    @Override
    public void onStart() {
        super.onStart();
        firebaserecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaserecyclerAdapter.stopListening();
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
