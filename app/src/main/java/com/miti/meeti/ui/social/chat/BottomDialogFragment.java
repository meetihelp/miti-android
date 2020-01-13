package com.miti.meeti.ui.social.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.miti.meeti.R;

public  class BottomDialogFragment extends BottomSheetDialogFragment {
    public static View view;
    public static ProgressBar p;
    public static BottomDialogFragment getInstance() {
        return new BottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        String myValue = mArgs.getString("userId");
        view = inflater.inflate(R.layout.bottomdialog_chatlist, container, false);
        p=view.findViewById(R.id.progressBar4);
        p.setVisibility(View.VISIBLE);
        ProfilePostRequest.getprofile(myValue);
//        ProfilePostRequest temp=new ProfilePostRequest();
        return view;
    }

}