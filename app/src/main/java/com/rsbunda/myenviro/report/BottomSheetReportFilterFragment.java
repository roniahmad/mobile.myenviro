package com.rsbunda.myenviro.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rsbunda.myenviro.R;

public class BottomSheetReportFilterFragment extends BottomSheetDialogFragment {

    public BottomSheetReportFilterFragment(){
        //Empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bottomsheet_filter_dac_fragment, container, false);
        return v;
    }

}
