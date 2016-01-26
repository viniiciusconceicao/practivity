package com.example.wvd.practivity;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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


import java.util.ArrayList;
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
        EntitiesAdapter adapter = new EntitiesAdapter(entities, mContext, new EntitiesAdapter.EntitiesAdapterClickListener() {
            @Override
            public void recyclerViewClick(int position) {

            }
        });
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

    @Override
    public String toString() {
        return TAG;
    }
/*
    private class AsyncDistance extends AsyncTask<ArrayList<Entities>,Void,Void>{

        protected LocationManager locationManager;
        public EntitiesLocationListener mEntitiesLocationListener;
        public double lati = 0.0;
        public double longi = 0.0;

        public AsyncDistance(Context mContext){

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        }

        @Override
        protected Void doInBackground(ArrayList<Entities>... params) {

            while (this.lati == 0.0) {

            }
            return null;
        }

        public class EntitiesLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {

                int lat = (int) location.getLatitude(); // * 1E6);
                int log = (int) location.getLongitude(); // * 1E6);
                int acc = (int) (location.getAccuracy());

                String info = location.getProvider();
                try {

                    // LocatorService.myLatitude=location.getLatitude();

                    // LocatorService.myLongitude=location.getLongitude();

                    lati = location.getLatitude();
                    longi = location.getLongitude();

                } catch (Exception e) {
                    // progDailog.dismiss();
                    // Toast.makeText(getApplicationContext(),"Unable to get Location"
                    // , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("OnProviderDisabled", "OnProviderDisabled");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("onProviderEnabled", "onProviderEnabled");
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.i("onStatusChanged", "onStatusChanged");

            }

        }
    }*/
}
