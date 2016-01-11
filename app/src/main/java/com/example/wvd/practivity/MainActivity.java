package com.example.wvd.practivity;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
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

import java.util.Stack;

public class MainActivity extends Activity implements FragmentCategory.OnCategoryClickedListener, FragmentActivities.OnActivityClickedListener {

    private static final String TAG = "MainActivity";
    public static final String TAG_CATEGORIE = "CAT";
    public static final String TAG_ACTIVITY = "ACT";

    private Toolbar toolbar;
    private SearchView mSearchView;

    public Stack<String> mFragmentStack;

    private FrameLayout fragment1_vertical;

    private PreferencesMan prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentStack = new Stack<String>();
        prefs = new PreferencesMan(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setActionBar(toolbar);
        toolbar.setLayoutTransition(new LayoutTransition());

        fragment1_vertical = (FrameLayout)findViewById(R.id.fragment1_vertical);

        Fragment aFrag = new FragmentCategory();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentStack.add(aFrag.toString());
        fragmentTransaction.add(R.id.fragment1_vertical,aFrag,aFrag.toString());
        fragmentTransaction.addToBackStack(aFrag.toString());
        fragmentTransaction.commit();
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
        switch(id) {
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

    @Override
    public void onCategoryClicked(Category category_clicked) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_CATEGORIE, category_clicked);
        Fragment aFrag = new FragmentActivities();
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
    public void onActivityClicked(Activities activities_clicked) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_ACTIVITY, activities_clicked);

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
    public void onBackPressed(){
        // from the stack we can get the latest fragment
        Fragment fragment = getFragmentManager().findFragmentByTag(mFragmentStack.peek());
        // If its an instance of Fragment1 I don't want to finish my activity, so I launch a Toast instead.
        if (fragment instanceof FragmentCategory){
            finish();
        }
        else{
            // Remove the framg
            removeFragment();
            super.onBackPressed();
        }
    }

    private void removeFragment(){
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
}
