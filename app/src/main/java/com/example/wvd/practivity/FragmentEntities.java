package com.example.wvd.practivity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wvd.practivity.Adapter.EntitiesAdapter;
import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.Data.Entities;
import com.example.wvd.practivity.Misc.JSONParser;


import java.util.List;

/**
 * Created by walterjgsp on 11/01/16.
 */
public class FragmentEntities extends Fragment {

    public static final String TAG = "FragmentEntities";

    private Context mContext;

    private List<Entities> entities;
    RecyclerView rv;
    TextView title;
    private Activities activities;

    public void initializeData(){
        JSONParser jsonParser = new JSONParser(mContext);
        entities=jsonParser.readEntities(activities.getActivities_id());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.category_fragment, container, false);
        // create your view using LayoutInflater

        title = (TextView) view.findViewById(R.id.categories_title);
        title.setText(activities.getName());

        rv = (RecyclerView)view.findViewById(R.id.rv);
        // 2. set layoutManger
        initializeData();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        // do your variables initialisations here except Views!!!
        EntitiesAdapter adapter = new EntitiesAdapter(entities, mContext);
        rv.setAdapter(adapter);

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        activities = (Activities) getArguments().getSerializable(MainActivity.TAG_ACTIVITY);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // initialise your views
    }

    public interface OnCategoryClickedListener{
        public void onCategoryClicked(Category category_clicked);
    }

    @Override
    public String toString() {
        return TAG;
    }
}
