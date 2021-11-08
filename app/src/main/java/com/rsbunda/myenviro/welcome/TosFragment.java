package com.rsbunda.myenviro.welcome;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.util.WelcomeUtils;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class TosFragment extends WelcomeFragment {
    //implements HospitalItemViewHolder.Callbacks

    private static final String TAG = makeLogTag(TosFragment.class);

    @Override
    public boolean shouldDisplay(Context context) {
        return !WelcomeUtils.isTosAccepted(context);
    }

    @Override
    protected View.OnClickListener getPrimaryButtonListener() {
        return new WelcomeFragmentOnClickListener(mActivity) {
            @Override
            public void onClick(View v) {
                // Ensure we don't run this fragment again.
                LOGD(TAG, "Marking TOS flag.");
                WelcomeUtils.markTosAccepted(mActivity, true);
                ((WelcomeActivity) mActivity).doNext();
            }
        };
    }

    @Override
    protected View.OnClickListener getSecondaryButtonListener() {
        return null;
    }

    @Override
    protected int getHeaderColorRes() {
        return R.color.colorPrimary;
    }

    @Override
    protected int getLogoDrawableRes() {
        return R.drawable.ic_logo;
    }

    @Override
    protected int getTitleRes() {
        return R.string.welcome_to_myenviro;
    }

    @Override
    protected String getPrimaryButtonText() {
        return getResourceString(R.string.accept);
    }

    @Override
    protected String getSecondaryButtonText() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.welcome_tos_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initListener(view);
    }

    private void initViews(final View v){
        //TODO
    }

    private void initListener(final View v){
        //TODO
    }

}
