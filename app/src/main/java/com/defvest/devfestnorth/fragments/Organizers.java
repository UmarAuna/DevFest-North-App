package com.defvest.devfestnorth.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.defvest.devfestnorth.models.organizers_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
    public class Organizers extends Fragment {
    View rootView;
    Context context;

    private FirebaseRecyclerAdapter<organizers_model, OrganisersView> firebaserecyclerAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference myref;
    public static Organizers newInstance(){
        Organizers fragment = new Organizers();
        return fragment;
    }
    public Organizers() {
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
        rootView =  inflater.inflate(R.layout.fragment_organizers, container, false);
        recyclerView = rootView.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        myref = FirebaseDatabase.getInstance().getReference().child("Organizers");
        myref.keepSynced(true);
        FirebaseRecyclerOptions<organizers_model> options = new FirebaseRecyclerOptions.Builder<organizers_model>().setQuery(myref,organizers_model.class).build();

        firebaserecyclerAdapter = new FirebaseRecyclerAdapter<organizers_model, OrganisersView>(options) {



            @SuppressLint("CheckResult")
            @Override
            protected void onBindViewHolder(OrganisersView viewholder, final int position, final organizers_model model) {
                viewholder.setName(model.getName());
                viewholder.setGDG(model.getGDG());
                RequestOptions placeholderRequest = new RequestOptions();
                placeholderRequest.placeholder(R.drawable.warning);
                viewholder.setPhoto(model.getPhoto());
                viewholder.setAbout(model.getAbout());

                viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Bundle extras = new Bundle();
                        Intent nextactivity = new Intent(v.getContext(), OrganizersDetail.class);
                        nextactivity.putExtra("name",model.getName());
                        nextactivity.putExtra("gdg",model.getGDG());
                        nextactivity.putExtra("photo",model.getPhoto());
                        nextactivity.putExtra("about",model.getAbout());
                        nextactivity.putExtras(extras);
                        v.getContext().startActivity(nextactivity);

                    }
                });
            }

            @Override
            public OrganisersView onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.organizers_custom_list,parent,false);
                return new OrganisersView(v);
            }
        };
        recyclerView.setAdapter(firebaserecyclerAdapter);
        return rootView;
    }
    public static class OrganisersView extends RecyclerView.ViewHolder {
        View mView;
        TextView name;
        TextView gdg;

        ImageView photo;
        TextView about;

        public OrganisersView(View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) itemView.findViewById(R.id.vname);
            gdg = (TextView) itemView.findViewById(R.id.vgdg);
            photo=  itemView.findViewById(R.id.profile_photo);
            about= (TextView) itemView.findViewById(R.id.vabout);
        }

        public void setName(String names) {
            name.setText(names);
        }
        public void setGDG(String gdgs) {
            gdg.setText(gdgs);
        }
        @SuppressLint("CheckResult")
        public void setPhoto(String photos) {
            RequestOptions placeholderRequest = new RequestOptions();
            placeholderRequest.placeholder(R.drawable.warning);
            Glide.with(mView.getContext()).load(photos).into(photo);
        }
            public void setAbout(String abouts ) {
            about.setText(abouts);
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


}
