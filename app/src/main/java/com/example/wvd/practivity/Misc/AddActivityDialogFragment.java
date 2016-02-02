package com.example.wvd.practivity.Misc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.wvd.practivity.R;

/**
 * Created by walterjgsp on 02/02/16.
 */
public class AddActivityDialogFragment extends DialogFragment {

    public static String TAG ="AddActivityDialogFragment";
    private Button cancelButton;
    private TextView title;

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

        title = (TextView) rootView.findViewById(R.id.title_dialog_text);
        title.setText(R.string.addActivityTitle);

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
