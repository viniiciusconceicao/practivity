package com.example.wvd.practivity.Misc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.wvd.practivity.R;

/**
 * Created by walterjgsp on 02/02/16.
 */
public class AddCategoryDialogFragment extends DialogFragment {

    public static String TAG ="AddCategoryDialogFragment";
    private Button cancelButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add_category, container,false);
        getDialog().setTitle(null);

        cancelButton = (Button) rootView.findViewById(R.id.category_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        // Do something else
        return rootView;
    }
}
