package com.example.wvd.practivity;

import android.Manifest;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.Misc.PreferencesMan;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Stack;

public class MainActivity extends Activity implements FragmentCategory.OnCategoryClickedListener, FragmentActivities.OnActivityClickedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    public static final String TAG_CATEGORIE = "CAT";
    public static final String TAG_ACTIVITY = "ACT";
    public static final String TAG_LONGI = "LONG";
    public static final String TAG_LATIT = "LATI";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Toolbar toolbar;
    private SearchView mSearchView;

    public Stack<String> mFragmentStack;

    private FrameLayout fragment1_vertical;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentStack = new Stack<String>();

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setActionBar(toolbar);
        toolbar.setLayoutTransition(new LayoutTransition());
        //toolbar.setLogo(R.mipmap.ic_launcher);
        //this.setTitle(null);

        fragment1_vertical = (FrameLayout) findViewById(R.id.fragment1_vertical);

        EnableGPS();

        Fragment aFrag = new FragmentCategory();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentStack.add(aFrag.toString());
        fragmentTransaction.add(R.id.fragment1_vertical, aFrag, aFrag.toString());
        fragmentTransaction.addToBackStack(aFrag.toString());
        fragmentTransaction.commit();

        // First we need to check availability of play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.e(TAG, "SearchOnQueryTextSubmit: " + query);
                        if (!mSearchView.isIconified()) {
                            mSearchView.setIconified(true);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                        return false;
                    }
                });
        }

        return super.onOptionsItemSelected(item);
    }

    //FRAGMENT FUNCTIONS
    @Override
    public void onCategoryClicked(Category category_clicked) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_CATEGORIE, category_clicked);
        Fragment aFrag = new FragmentActivities();
        aFrag.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fragment_animation_fade_in, R.animator.fragment_animation_fade_out);

        Fragment currentFragment = getFragmentManager().findFragmentByTag(mFragmentStack.peek());
        fragmentTransaction.hide(currentFragment);

        fragmentTransaction.add(R.id.fragment1_vertical, aFrag, aFrag.toString());
        fragmentTransaction.addToBackStack(aFrag.toString());
        mFragmentStack.add(aFrag.toString());

        fragmentTransaction.commit();
    }

    @Override
    public void onActivityClicked(Activities activities_clicked) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_ACTIVITY, activities_clicked);
        bundle.putDouble(TAG_LATIT, latitude);
        bundle.putDouble(TAG_LONGI, longitude);

        Fragment aFrag = new FragmentEntities();
        aFrag.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fragment_animation_fade_in, R.animator.fragment_animation_fade_out);
        //fragmentTransaction.replace(R.id.fragment1_vertical, aFrag).addToBackStack("tag");

        Fragment currentFragment = getFragmentManager().findFragmentByTag(mFragmentStack.peek());
        fragmentTransaction.hide(currentFragment);

        fragmentTransaction.add(R.id.fragment1_vertical, aFrag, aFrag.toString());
        fragmentTransaction.addToBackStack(aFrag.toString());
        mFragmentStack.add(aFrag.toString());

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        // from the stack we can get the latest fragment
        Fragment fragment = getFragmentManager().findFragmentByTag(mFragmentStack.peek());
        // If its an instance of Fragment1 I don't want to finish my activity, so I launch a Toast instead.
        if (fragment instanceof FragmentCategory) {
            finish();
        } else {
            // Remove the framg
            removeFragment();
            super.onBackPressed();
        }
    }

    private void removeFragment() {
        // remove the current fragment from the stack.
        mFragmentStack.pop();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // get fragment that is to be shown (in our case fragment1).
        Fragment fragment = getFragmentManager().findFragmentByTag(mFragmentStack.peek());
        // This time I set an animation with no fade in, so the user doesn't wait for the animation in back press
        transaction.setCustomAnimations(R.animator.fragment_animation_no_fade_in, R.animator.fragment_animation_fade_out);
        // We must use the show() method.
        transaction.show(fragment);
        transaction.commit();
    }

    //GPS LOCATION FUNCTIONS
    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());

    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    /**
     * Method to get the Location
     */
    private void getLocation() {

        PreferencesMan prefs = new PreferencesMan(getApplicationContext());

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request missing location permission.
            ActivityCompat.requestPermissions(this  ,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);
            //return;
        }

        if(mGoogleApiClient == null)
            Log.e(TAG,"MERDA");

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            prefs.setLocation(mLastLocation);
        } else {
            Log.e(TAG, "(Couldn't get the location. Make sure location is enabled on the device)");
            mLastLocation = prefs.getLocation();
        }
    }

    public void EnableGPS(){
       LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.gps_not_found_title);  // GPS not found
            builder.setMessage(R.string.gps_not_found_message); // Want to enable?
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferencesMan prefs = new PreferencesMan(getApplicationContext());
                    mLastLocation = prefs.getLocation();
                    Toast.makeText(getApplicationContext(), R.string.last_saved_location, Toast.LENGTH_LONG);
                }
            });
            builder.create().show();
            return;
        }
    }
}