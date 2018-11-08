package com.defvest.devfestnorth.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.activities.SpeakersDetail;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Speaker_Adapter extends RecyclerView.Adapter<Speaker_Adapter.SearchViewHolder> {
    Context context;
    ArrayList<String> NameList;
    ArrayList<String> TopicList;
    ArrayList<String> WorkList;
    ArrayList<String> ProfilePicList;

    ArrayList<String> AboutList;
    ArrayList<String> TwitterList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView name, twitter,work;
        TextView aboutmodel,topicmodel;

        public SearchViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.query_image);
            name = itemView.findViewById(R.id.names);
            twitter = itemView.findViewById(R.id.twitters);
            work = itemView.findViewById(R.id.works);

            aboutmodel = itemView.findViewById(R.id.abouts);
            topicmodel = itemView.findViewById(R.id.topics);
        }
    }

    public Speaker_Adapter(Context context, ArrayList<String> profilePicList, ArrayList<String> NameList, ArrayList<String> TwitterList, ArrayList<String> WorkList, ArrayList<String>AboutList,
                           ArrayList<String>TopicList) {
        this.context = context;
        this.ProfilePicList = profilePicList;
        this.NameList = NameList;
        this.TwitterList = TwitterList;
        this.WorkList = WorkList;

        this.AboutList = AboutList;
        this.TopicList = TopicList;

    }

    @Override
    public Speaker_Adapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.speakers_custom_list, parent, false);
        return new Speaker_Adapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {
        holder.name.setText(NameList.get(position));
        holder.twitter.setText(TwitterList.get(position));
        holder.topicmodel.setText(TopicList.get(position));

        holder.aboutmodel.setText(AboutList.get(position));
        holder.work.setText(WorkList.get(position));

        RequestOptions placeholderRequest = new RequestOptions();
        placeholderRequest.placeholder(R.drawable.warning);

        Glide.with(context).setDefaultRequestOptions(placeholderRequest).load(ProfilePicList.get(position)).into(holder.profileImage);
        //Glide.with(context).asBitmap().load(profilePicList.get(position)).into(holder.profileImage);



       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();

                Intent nextactivity = new Intent(v.getContext(),SpeakersDetail.class);
                nextactivity.putExtra("Name",NameList.get(position));
                nextactivity.putExtra("Twitter",TwitterList.get(position));
                nextactivity.putExtra("Topic",TopicList.get(position));
                nextactivity.putExtra("Photo", ProfilePicList.get(position));

                nextactivity.putExtra("Work",WorkList.get(position));
                nextactivity.putExtra("About",AboutList.get(position));

                nextactivity.putExtras(extras);
                v.getContext().startActivity(nextactivity);
            }
        });

    }
    @Override
    public int getItemCount() {
        return NameList.size();
    }
}
