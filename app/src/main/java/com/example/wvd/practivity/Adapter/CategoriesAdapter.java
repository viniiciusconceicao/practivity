package com.example.wvd.practivity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.R;

import java.util.List;

/**
 * Created by Vi on 1/5/2016.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private static final String TAG = "CategoriesADapter";

    private Context mContext;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView categoryName;
        TextView categoryCount;
        Context mContext;

        public CategorieClickListener listener;

        //listener passed to viewHolder
        public interface CategorieClickListener {
            void categorieOnClick(int position);
        }

        CategoryViewHolder(View itemView,Context context,CategorieClickListener listener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            categoryName = (TextView)itemView.findViewById(R.id.category_name);
            categoryCount = (TextView)itemView.findViewById(R.id.category_counter);
            mContext=context;
            this.listener=listener;
        }

        @Override
        public void onClick(View v) {
            listener.categorieOnClick(getPosition());
        }
    }

    List<Category> categories;
    public CategoriesAdapterClickListener recListener;

    public CategoriesAdapter(List<Category> categories, Context context,CategoriesAdapterClickListener recListener){
        this.categories = categories;
        this.mContext=context;
        this.recListener=recListener;
    }

    public interface CategoriesAdapterClickListener {
        void recyclerViewClick(int position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_cardview, viewGroup, false);
        CategoryViewHolder cvh = new CategoryViewHolder(v,mContext, new CategoryViewHolder.CategorieClickListener(){
            @Override
            public void categorieOnClick(int position) {
                recListener.recyclerViewClick(position);
            }
        });
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
