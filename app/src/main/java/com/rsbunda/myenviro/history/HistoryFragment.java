package com.rsbunda.myenviro.history;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.login.LoginActivity;
import com.rsbunda.myenviro.login.RegisterAccountActivity;
import com.rsbunda.myenviro.settings.SettingsActivity;
import com.rsbunda.myenviro.settings.SettingsUtils;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.UIUtils;

import static android.app.Activity.RESULT_OK;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class HistoryFragment extends Fragment {

    private static final String TAG = makeLogTag(HistoryFragment.class);

    private static final int FEEDBACK_REQUEST = 100;

    //Delay to update user information
    private static final int HANDLER_LAUNCH_DELAY = 300;

    private Handler mHandler = new Handler();

    private RecyclerView mRecyclerView;

    private View mEmptyContainer;
    private View mUnderContainer;
    private View mNoticeContainer;

    private Button mButtonLogin;
    private Button mButtonRegister;
    private ProgressBar mProgressBar;

    private TextView mTxtEmpty;

    private Context mContext;

    private SharedPreferences.OnSharedPreferenceChangeListener mSettingsChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                      String key) {
                    if(LoginUtils.PREF_USER_HAS_LOGIN.equals(key)){
                        initState();

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //
                            }
                        }, HANDLER_LAUNCH_DELAY);

                    }else if (SettingsUtils.PREF_USER_HISTORY_LIST.equals(key)){
                        initState();
                    }
                }
            };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        mContext = getContext();

        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mEmptyContainer = (View) view.findViewById(R.id.empty_container);
        mEmptyContainer = (View) view.findViewById(R.id.underconstruction_container);
        mNoticeContainer = (View) view.findViewById(R.id.notice_container);

        mTxtEmpty = (TextView) view.findViewById(R.id.tv_empty);
        UIUtils.setTextMaybeHtml(mTxtEmpty, getString(R.string.empty_history));

        mButtonLogin = (Button) view.findViewById(R.id.login_link);
        mButtonRegister = (Button) view.findViewById(R.id.register_link);

        initListeners();

        return view;
    }

    private  void initListeners(){
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startLoginActivity(getActivity());
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccountActivity.startRegisterAccountActivity(getActivity());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initState();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        sp.registerOnSharedPreferenceChangeListener(mSettingsChangeListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mSettingsChangeListener != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
            sp.unregisterOnSharedPreferenceChangeListener(mSettingsChangeListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == FEEDBACK_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //TODO
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_setting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Activity activity = getActivity();
        switch (item.getItemId()) {
            case R.id.menu_setting :
                SettingsActivity.startSettingsActivity(activity);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initState(){
        final boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);
        if(!userHasLogin){
            mEmptyContainer.setVisibility(View.GONE);
            mNoticeContainer.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else{

            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    //TODO
                }
            }, HANDLER_LAUNCH_DELAY);
        }
    }

}
