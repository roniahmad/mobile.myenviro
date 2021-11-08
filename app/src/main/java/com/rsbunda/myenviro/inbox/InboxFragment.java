package com.rsbunda.myenviro.inbox;

import android.app.Activity;
import android.content.Context;
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


import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class InboxFragment extends Fragment{

    private static final String TAG = makeLogTag(InboxFragment.class);

    //Delay to update user information
    private static final int HANDLER_LAUNCH_DELAY = 300;

    private Handler mHandler = new Handler();

    private RecyclerView mRecyclerView;

    private ProgressBar mProgressBar;

    private View mEmptyContainer;
    private View mUnderContainer;
    private View mNoticeContainer;

    private Button mButtonLogin;
    private Button mButtonRegister;

    private TextView mTxtEmpty;

    private Context mContext;

    private SharedPreferences.OnSharedPreferenceChangeListener mSettingsChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                      String key) {
                    if(LoginUtils.PREF_USER_HAS_LOGIN.equals(key)){

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initState();
                            }
                        }, HANDLER_LAUNCH_DELAY);

                    }else if(SettingsUtils.PREF_USER_NEWS_LIST.equals(key)){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initState();
                            }
                        }, HANDLER_LAUNCH_DELAY);
                    }
                }
            };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inbox_fragment, container, false);

        mContext = getContext();

        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mEmptyContainer = (View) view.findViewById(R.id.empty_container);
        mUnderContainer = (View) view.findViewById(R.id.underconstruction_container);
        mNoticeContainer = (View) view.findViewById(R.id.notice_container);

        mTxtEmpty = (TextView) view.findViewById(R.id.tv_empty);
        UIUtils.setTextMaybeHtml(mTxtEmpty, getString(R.string.empty_news));

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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



    //check state of user
    private void initState(){
        final boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);

        if(!userHasLogin){
            mNoticeContainer.setVisibility(View.VISIBLE);
            //TODO
        }else{
            mNoticeContainer.setVisibility(View.GONE);
            //TODO
        }
    }

}
