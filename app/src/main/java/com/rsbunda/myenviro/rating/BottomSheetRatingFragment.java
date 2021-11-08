package com.rsbunda.myenviro.rating;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rsbunda.myenviro.R;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class BottomSheetRatingFragment extends BottomSheetDialogFragment {

    private static final String TAG = makeLogTag(BottomSheetRatingFragment.class);

    public BottomSheetRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bottom_sheet_rating_fragment, container, false);
        return v;
    }

}
