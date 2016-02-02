package com.example.wvd.practivity;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toast;
import com.example.wvd.practivity.Adapter.EntitiesAdapter;
import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Entities;
import com.example.wvd.practivity.Misc.JSONParser;

import java.util.Collections;
import java.util.Comparator;
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
    private double longitude;
    private double latitude;
    private Location userLocation;

    public void initializeData(){
        JSONParser jsonParser = new JSONParser(mContext);
        entities=jsonParser.readEntities(activities.getActivities_id());
        for(Entities e:entities){
            Location locationB = new Location("Entitie");

            locationB.setLatitude(e.getLatitude());
            locationB.setLongitude(e.getLongitude());

            double distance = userLocation.distanceTo(locationB);

            e.setDistancefromUser(distance);
        }

        Collections.sort(entities,EntitiesComparator);

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
        EntitiesAdapter adapter = new EntitiesAdapter(entities, mContext, new EntitiesAdapter.EntitiesAdapterClickListener() {
            @Override
            public void recyclerViewClick(int position) {
            }
        },longitude,latitude);
        rv.setAdapter(adapter);

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        activities = (Activities) getArguments().getSerializable(MainActivity.TAG_ACTIVITY);
        latitude = getArguments().getDouble(MainActivity.TAG_LATIT);
        longitude = getArguments().getDouble(MainActivity.TAG_LONGI);
        userLocation = new Location("UserLocation");
        userLocation.setLongitude(longitude);
        userLocation.setLatitude(latitude);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // initialise your views
    }

    public static Comparator<Entities> EntitiesComparator = new Comparator<Entities>() {
        @Override
        public int compare(Entities lhs, Entities rhs) {
            return new Float(lhs.getDistancefromUser()).compareTo(new Float(rhs.getDistancefromUser()));
        }
    };

    @Override
    public String toString() {
        return TAG;
    }
}
