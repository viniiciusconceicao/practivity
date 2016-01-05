package com.example.wvd.practivity.Adapter;

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

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView categoryName;

        CategoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            categoryName = (TextView)itemView.findViewById(R.id.category_name);
        }
    }

    List<Category> categories;

    RVAdapter(List<Category> categories){
        this.categories = categories;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_cardview, viewGroup, false);
        CategoryViewHolder cvh = new CategoryViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.categoryName.setText(categories.get(i).getName());

    }

}
