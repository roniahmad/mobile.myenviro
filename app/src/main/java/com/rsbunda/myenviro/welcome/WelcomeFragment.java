package com.rsbunda.myenviro.welcome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.TextView;

import com.rsbunda.myenviro.R;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public abstract class WelcomeFragment extends Fragment {

    private static final String TAG = makeLogTag(WelcomeFragment.class);
    protected Activity mActivity;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            LOGD(TAG, "Attaching to activity");
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView titleTV = (TextView) getActivity().findViewById(R.id.title);

        if (titleTV != null) {
            // Set activity to fragment title text view and fire accessibility event so the title
            // gets read by talkback service.
            mActivity.setTitle(titleTV.getText());
            mActivity.getWindow().getDecorView()
                    .sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        LOGD(TAG, "Creating View");

        if (mActivity instanceof WelcomeFragmentContainer) {
            WelcomeFragmentContainer activity = (WelcomeFragmentContainer) mActivity;
            attachToPrimaryButton(activity.getPrimaryButton());
            attachToSecondaryButton(activity.getSecondaryButton());
            activity.setButtonBarVisibility(shouldShowButtonBar());
        }
        return view;
    }

    /**
     * Attaches to the primary action button of {@link WelcomeFragmentContainer}.
     *
     * @param button the ui element to attach to.
     */
    protected void attachToPrimaryButton(Button button) {
        button.setText(getPrimaryButtonText());
        button.setOnClickListener(getPrimaryButtonListener());
    }

    public abstract boolean shouldDisplay(Context context);

    /**
     * Attaches to the secondary action button of the WelcomeFragmentContainer.
     *
     * @param button the ui element to attach to.
     */
    protected void attachToSecondaryButton(Button button) {
        String secondaryButtonText = getSecondaryButtonText();
        View.OnClickListener secondaryButtonClickListener = getSecondaryButtonListener();
        if (!TextUtils.isEmpty(secondaryButtonText) && secondaryButtonClickListener != null) {
            button.setVisibility(View.VISIBLE);
            button.setText(secondaryButtonText);
            button.setOnClickListener(secondaryButtonClickListener);
        }
    }


    /**
     * Gets a resource string.
     *
     * @param id the id of the string resource.
     * @return the value of the resource or null.
     */
    protected String getResourceString(int id) {
        if (mActivity != null) {
            return mActivity.getResources().getString(id);
        }
        return null;
    }

    /**
     * Returns the text for the primary action button. Example: "Accept".
     */
    protected abstract String getPrimaryButtonText();

    /**
     * Returns the text for the secondary action button. Example: "Cancel".
     */
    protected abstract String getSecondaryButtonText();

    /**
     * Returns the {@link android.view.View.OnClickListener} for the primary action click event.
     */
    protected abstract View.OnClickListener getPrimaryButtonListener();

    /**
     * Returns the {@link android.view.View.OnClickListener} for the secondary action click event.
     */
    protected abstract View.OnClickListener getSecondaryButtonListener();

    /**
     * Returns a color resource id for the header background.
     */
    protected abstract @ColorRes
    int getHeaderColorRes();

    /**
     * Returns a drawable resource id for the logo in the header.
     */
    protected abstract @DrawableRes
    int getLogoDrawableRes();

    protected abstract @StringRes
    int getTitleRes();

    /**
     * Returns whether the button bar should be displayed.
     */
    protected boolean shouldShowButtonBar() {
        return true;
    }

    /**
     * A convenience {@link android.view.View.OnClickListener} for the common use cases.
     */
    protected abstract class WelcomeFragmentOnClickListener implements View.OnClickListener {
        Activity mActivity;

        /**
         * Construct a listener that handles the transition to the next activity.
         *
         * @param activity the Activity to interact with.
         */
        public WelcomeFragmentOnClickListener(Activity activity) {
            mActivity = activity;
        }
    }

    /**
     * The receiver for the action to be performed on a button click.
     */
    interface WelcomeFragmentClickAction {
        public void doAction(Context context);
    }

    /**
     * The Container for the welcome fragments.
     */
    interface WelcomeFragmentContainer {

        /**
         * Returns the primary action button from the container.
         */
        Button getPrimaryButton();

        /**
         * Enables the primary action button in the container.
         *
         * @param enabled enabled true to enable button, false to disable it.
         */
        void setPrimaryButtonEnabled(Boolean enabled);

        /**
         * Returns the secondary action button from the container.
         */
        Button getSecondaryButton();

        void setButtonBarVisibility(boolean isVisible);
    }

}
