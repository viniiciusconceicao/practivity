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
import android.widget.AdapterView;
import android.widget.TextView;

import android.widget.Toast;
import com.example.wvd.practivity.Adapter.ActivitiesAdapter;
import com.example.wvd.practivity.Adapter.CategoriesAdapter;
import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;

import java.util.List;

/**
 * Created by walterjgsp on 07/01/16.
 */
public class FragmentActivities extends Fragment {

    public static final String TAG = "FragmentActivities";

    private Context mContext;

    private Category category;
    private List<Activities> activities;
    RecyclerView rv;
    private TextView title;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.category_fragment, container, false);
        // create your view using LayoutInflater

        title = (TextView) view.findViewById(R.id.categories_title);
        title.setText(category.getName());

        rv = (RecyclerView) view.findViewById(R.id.rv);
        // 2. set layoutManger
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        // do your variables initialisations here except Views!
        ActivitiesAdapter adapter = new ActivitiesAdapter(activities,mContext,new ActivitiesAdapter.ActivitiesAdapterClickListener() {
            @Override
            public void recyclerViewClick(int position) {
                try{
                    ((OnActivityClickedListener) getActivity()).onActivityClicked(activities.get(position));
                }catch (ClassCastException cce){
                    Log.e(TAG, "Interface not implemented on Activity");
                }
            }
        });

        rv.setAdapter(adapter);

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        category = (Category)getArguments().getSerializable(MainActivity.TAG_CATEGORIE);
        activities = category.getActivities();
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do what you want
                Toast.makeText(mContext, TAG, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initialise your views
    }

    public interface OnActivityClickedListener{
        public void onActivityClicked(Activities activities_clicked);
    }

    @Override
    public String toString() {
        return TAG;
    }
}
