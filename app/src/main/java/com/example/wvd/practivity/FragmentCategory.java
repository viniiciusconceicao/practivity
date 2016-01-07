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
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.CardView;

import com.example.wvd.practivity.Adapter.RVAdapter;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.Misc.JSONParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walterjgsp on 05/01/16.
 */
public class FragmentCategory extends Fragment {

    private static final String TAG = "FragmentCategory";

    private Context mContext;

    private List<Category> categories;
    RecyclerView rv;
    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    private void initializeData() {
        JSONParser json = new JSONParser(mContext);
        categories = json.readCategories();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.category_fragment, container, false);
        // create your view using LayoutInflater

        rv = (RecyclerView)view.findViewById(R.id.rv);
        // 2. set layoutManger
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initializeData();
        // do your variables initialisations here except Views!!!
        RVAdapter adapter = new RVAdapter(categories,mContext);
        rv.setAdapter(adapter);

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // initialise your views
    }
}
