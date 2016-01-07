package com.example.wvd.practivity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.R;

import java.util.List;

/**
 * Created by Vi on 1/5/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CategoryViewHolder>{

    private static final String TAG = "RVADapter";

    private Context mContext;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView categoryName;
        TextView categoryCount;
        Context mContext;

        CategoryViewHolder(View itemView,Context context) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            categoryName = (TextView)itemView.findViewById(R.id.category_name);
            categoryCount = (TextView)itemView.findViewById(R.id.category_counter);
            mContext=context;
        }
    }

    List<Category> categories;

    public RVAdapter(List<Category> categories,Context context){
        this.categories = categories;
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_cardview, viewGroup, false);
        CategoryViewHolder cvh = new CategoryViewHolder(v,mContext);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.categoryName.setText(categories.get(i).getName());
        categoryViewHolder.categoryCount.setText(String.valueOf(categories.get(i).getActivities().size()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
