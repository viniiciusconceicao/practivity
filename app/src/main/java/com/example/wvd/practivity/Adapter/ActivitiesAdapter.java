package com.example.wvd.practivity.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.MainActivity;
import com.example.wvd.practivity.Misc.JSONParser;
import com.example.wvd.practivity.R;

import java.util.List;

/**
 * Created by Vi on 1/5/2016.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder> {

    public static final String TAG = "ActivitiesADapter";

    private Context mContext;
    private JSONParser jsonParser;

    public static class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView activityName;
        TextView activityCount;
        Context mContext;

        public ActivitiesClickListener listener;

        ActivityViewHolder(View itemView, Context context,ActivitiesClickListener listener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            activityName = (TextView)itemView.findViewById(R.id.activities_name);
            activityCount = (TextView)itemView.findViewById(R.id.activities_counter);
            mContext=context;
            this.listener=listener;
        }

        //listener passed to viewHolder
        public interface ActivitiesClickListener {
            void activityOnClick(int position);
        }

        @Override
        public void onClick(View v) {
            listener.activityOnClick(getPosition());
        }
    }

    List<Activities> activities;
    public ActivitiesAdapterClickListener recListener;

    public ActivitiesAdapter(List<Activities> activities, Context context,ActivitiesAdapterClickListener recListener){
        this.activities = activities;
        this.mContext=context;
        this.jsonParser = new JSONParser(context);
        this.recListener=recListener;
    }

    public interface ActivitiesAdapterClickListener {
        void recyclerViewClick(int position);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_cardview, viewGroup, false);
        ActivityViewHolder cvh = new ActivityViewHolder(v,mContext, new ActivityViewHolder.ActivitiesClickListener(){
            @Override
            public void activityOnClick(int position) {
                recListener.recyclerViewClick(position);
            }
        });
        return cvh;
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder categoryViewHolder, int i) {
        categoryViewHolder.activityName.setText(activities.get(i).getName());
        categoryViewHolder.activityCount.setText(String.valueOf(jsonParser.countEntitiesActivity(activities.get(i).getActivities_id())));
        //categoryViewHolder.activityCount.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
