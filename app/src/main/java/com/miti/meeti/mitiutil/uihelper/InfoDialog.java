package com.miti.meeti.mitiutil.uihelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.miti.meeti.R;

public class InfoDialog extends DialogFragment {
    int mNum;
    String text;
    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public InfoDialog(String text){
        this.text=text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mNum = getArguments().getInt("num");
        // Pick a style based on the num.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_dialog, container, false);
        TextView tv=v.findViewById(R.id.info_dialog_textview);
        tv.setText(text);
        return v;
    }
}
