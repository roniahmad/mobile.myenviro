package com.rsbunda.myenviro.teams;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rsbunda.myenviro.R;

public class BottomSheetTeamsFragment extends BottomSheetDialogFragment {

    private View mCardTeamMember;
    private View mCardDac;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bottomsheet_teams_fragment, container, false);
        mContext = getContext();

        initViews(v);
        initListeners();
        return v;
    }

    private void initViews(final View v){
        mCardTeamMember = (View) v.findViewById(R.id.card_teams_member);
        mCardDac = (View) v.findViewById(R.id.card_teams_daily_activity);
    }

    private void initListeners(){
        mCardTeamMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle =  getArguments();
//                final int mJosId = bundle.getInt(CONST_JOS_ID);
//
//                Intent josManPowerIntent = new Intent(mContext, JmpdActivity.class);
//                josManPowerIntent.putExtra(CONST_JOS_ID, mJosId);
//                mContext.startActivity(josManPowerIntent);
//                dismiss();
                Toast.makeText(mContext, getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
            }
        });

        mCardDac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle =  getArguments();
//                final int mJosId = bundle.getInt(CONST_JOS_ID);
//                final int mJobIs = bundle.getInt(CONST_JOB_ID);
//
//                Intent dacIntent = new Intent(mContext, DacActivity.class);
//                dacIntent.putExtra(CONST_JOS_ID, mJosId);
//                dacIntent.putExtra(CONST_JOB_ID, mJobIs);
//
//                mContext.startActivity(dacIntent);
//                dismiss();
                Toast.makeText(mContext, getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
