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
import com.example.wvd.practivity.Data.Entities;
import com.example.wvd.practivity.Misc.JSONParser;
import com.example.wvd.practivity.R;

import java.util.List;

/**
 * Created by walterjgsp on 11/01/16.
 */
public class EntitiesAdapter extends RecyclerView.Adapter<EntitiesAdapter.ActivityViewHolder> {

    public static final String TAG = "EntitiesADapter";

    private Context mContext;

    private JSONParser jsonParser;

    public static class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView activityName;
        Context mContext;

        ActivityViewHolder(View itemView, Context context) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            activityName = (TextView) itemView.findViewById(R.id.entitie_name);
            mContext = context;
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(mContext, "The Item Clicked is: " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    List<Entities> entities;

    public EntitiesAdapter(List<Entities> entities, Context context) {
        this.entities = entities;
        this.mContext = context;
        this.jsonParser = new JSONParser(context);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entities_cardview, viewGroup, false);
        ActivityViewHolder cvh = new ActivityViewHolder(v, mContext);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder categoryViewHolder, int i) {
        categoryViewHolder.activityName.setText(entities.get(i).getNome());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}