package com.example.wvd.practivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wvd.practivity.Adapter.CategoriesAdapter;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.Misc.JSONParser;
import com.example.wvd.practivity.Misc.PreferencesMan;

import java.util.List;

/**
 * Created by walterjgsp on 05/01/16.
 */
public class FragmentCategory extends Fragment {

    public static final String TAG = "FragmentCategory";

    private Context mContext;

    private List<Category> categories;
    RecyclerView rv;
    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    private void initializeData() {
        JSONParser json = new JSONParser(mContext);
        PreferencesMan prefs = new PreferencesMan(mContext);
        if(prefs.getJSON_version() != json.getJSONVersion())
            prefs.setJSON_version(json.getJSONVersion());
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
        CategoriesAdapter adapter = new CategoriesAdapter(categories, mContext, new CategoriesAdapter.CategoriesAdapterClickListener() {
            @Override
            public void recyclerViewClick(int position) {
                try{
                    ((OnCategoryClickedListener) getActivity()).onCategoryClicked(categories.get(position));
                }catch (ClassCastException cce){
                    Log.e(TAG,"Interface not implemented on Activity");
                }
            }
        });
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

    public interface OnCategoryClickedListener{
        public void onCategoryClicked(Category category_clicked);
    }

    @Override
    public String toString() {
        return TAG;
    }
}
