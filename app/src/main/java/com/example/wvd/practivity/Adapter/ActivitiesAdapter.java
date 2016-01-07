package com.example.wvd.practivity.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.R;

import java.util.List;

/**
 * Created by Vi on 1/5/2016.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder> {

    private static final String TAG = "ActivitiesADapter";

    private Context mContext;

    public static class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView activityName;
        TextView activityCount;
        Context mContext;

        ActivityViewHolder(View itemView, Context context) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            activityName = (TextView)itemView.findViewById(R.id.category_name);
            activityCount = (TextView)itemView.findViewById(R.id.category_counter);
            mContext=context;
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(mContext,"The Item Clicked is: "+getPosition(), Toast.LENGTH_SHORT).show();

        }
    }

    List<Activities> activities;

    public ActivitiesAdapter(List<Activities> activities, Context context){
        this.activities = activities;
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_cardview, viewGroup, false);
        ActivityViewHolder cvh = new ActivityViewHolder(v,mContext);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder categoryViewHolder, int i) {
        categoryViewHolder.activityName.setText(activities.get(i).getName());
        //categoryViewHolder.activityCount.setText(String.valueOf(activities.get(i).getActivities().size()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
