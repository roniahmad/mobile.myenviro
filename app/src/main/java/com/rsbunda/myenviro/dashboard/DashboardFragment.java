package com.rsbunda.myenviro.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import android.widget.Toast;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.dashboard.jos.JosAdapter;
import com.rsbunda.myenviro.dashboard.jos.JosItemViewHolder;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.model.sales.Jos;
import com.rsbunda.myenviro.io.model.sales.JosData;
import com.rsbunda.myenviro.login.LoginActivity;
import com.rsbunda.myenviro.login.RegisterAccountActivity;
import com.rsbunda.myenviro.manpower.ManPowerActivity;
import com.rsbunda.myenviro.report.ReportActivity;
import com.rsbunda.myenviro.settings.SettingsActivity;
import com.rsbunda.myenviro.ui.widget.LinePagerIndicatorDecoration;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.UIUtils;

import static com.rsbunda.myenviro.constant.CommonConstant.CONST_JOS_CLIENT_ID;
import static com.rsbunda.myenviro.constant.CommonConstant.CONST_JOS_ID;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment
        implements JosItemViewHolder.Callbacks{

    private static final String TAG = makeLogTag(DashboardFragment.class);

    //Delay to update user information
    private static final int HANDLER_LAUNCH_DELAY = 300;

    private Handler mHandler = new Handler();

    private List<Jos> mListJos;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private JosAdapter mJosAdapter;
    private ProgressBar mProgressBar;
    private View mViewEmptyContainer;
    private View mViewNoticeContainer;
    private View mControlPanelContainer;

    //toolbox
    //toolbox
    private View mCardComplaint;
    private View mCardTeams;
    private View mCardDocument;
    private View mCardReport;
//    private View mCardTools;
//    private View mCardRequest;
    private View mCardPerformance;
    private View mCardNotification;

    private Button mBtnLogin;
    private Button mBtnRegister;

    private TextView mTxtEmpty;

    private StringBuilder mBuffer = new StringBuilder();

    private Context mContext;

    private SharedPreferences.OnSharedPreferenceChangeListener mSettingsChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                      String key) {
                    if(LoginUtils.PREF_USER_HAS_LOGIN.equals(key)){
                        initState();
                    }
                }
            };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        mContext = getContext();

        initView(view);
        initListeners();

        return view;
    }

    private void initView(final View view){

        mLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mViewEmptyContainer = (View) view.findViewById(R.id.empty_container);
        mViewNoticeContainer = (View) view.findViewById(R.id.notice_container);
        mControlPanelContainer = (View) view.findViewById(R.id.control_panel_container);

        //toolbox
        mCardComplaint = (View) view.findViewById(R.id.card_complaint);
        mCardTeams = (View) view.findViewById(R.id.card_teams);
        mCardDocument = (View) view.findViewById(R.id.card_document);
        mCardReport = (View) view.findViewById(R.id.card_report);
//        mCardTools = (View) view.findViewById(R.id.card_tools);
//        mCardRequest = (View) view.findViewById(R.id.card_request);
        mCardPerformance = (View) view.findViewById(R.id.card_performance);
        mCardNotification = (View) view.findViewById(R.id.card_notification);

        mViewNoticeContainer.setVisibility(View.GONE);
        mViewEmptyContainer.setVisibility(View.GONE);
        mControlPanelContainer.setVisibility(View.GONE);

        mTxtEmpty = (TextView) view.findViewById(R.id.tv_empty);
        UIUtils.setTextMaybeHtml(mTxtEmpty, mContext.getString(R.string.empty_schedule));

        mBtnLogin = (Button) view.findViewById(R.id.login_link);
        mBtnRegister = (Button) view.findViewById(R.id.register_link);

        mListJos = Collections.EMPTY_LIST;

    }

    private  void initListeners(){
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startLoginActivity(getActivity());
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccountActivity.startRegisterAccountActivity(getActivity());
            }
        });

        mCardComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                int x = mLayoutManager.findFirstCompletelyVisibleItemPosition();
//                Jos j = mListJos.get(x);
//                Toast.makeText(mContext, "Item "+ j.getJosNumber(), Toast.LENGTH_SHORT).show();

                Toast.makeText(mContext, getResources().getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();

            }
        });

        mCardTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                Jos j = mListJos.get(x);
                int josId = j.getId();

                Bundle args = new Bundle();
                args.putInt(CONST_JOS_ID, josId);
                Intent manPowerIntent = new Intent(mContext, ManPowerActivity.class);
                manPowerIntent.putExtra(CONST_JOS_ID, josId);
                mContext.startActivity(manPowerIntent);


//                args.putInt(CONST_JOB_ID, jobId);


                //                Bundle bundle =  getArguments();
//                final int mJosId = bundle.getInt(CONST_JOS_ID);
//
//                Intent josManPowerIntent = new Intent(mContext, JmpdActivity.class);
//                josManPowerIntent.putExtra(CONST_JOS_ID, mJosId);
//                mContext.startActivity(josManPowerIntent);



//                Bundle args = new Bundle();
//                args.putInt(CONST_JOS_ID, josId);
//                args.putInt(CONST_JOB_ID, jobId);

//                BottomSheetTeamsFragment bottomSheetFragment = new BottomSheetTeamsFragment();
//                bottomSheetFragment.setArguments(args);
//                bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

            }
        });

        mCardDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, getResources().getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
            }
        });

        mCardReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int x = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                    Jos j = mListJos.get(x);

                    Intent dacIntent = new Intent(mContext, ReportActivity.class);
                    dacIntent.putExtra(CONST_JOS_ID, j.getId());
                    dacIntent.putExtra(CONST_JOS_CLIENT_ID, j.getClientId());
                    mContext.startActivity(dacIntent);

//                    Toast.makeText(mContext, "Item "+ j.getJosNumber(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(mContext, mContext.getString(R.string.select_jos_first), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        });

//        mCardTools.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, getResources().getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
//            }
//        });

//        mCardRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, getResources().getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
//            }
//        });

        mCardPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, getResources().getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
            }
        });

        mCardNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, getResources().getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJobOrder() {
        mProgressBar.setVisibility(View.VISIBLE);
        mListJos.clear();

        final String token = "Bearer "+LoginUtils.getUserToken(mContext);
        final String client = LoginUtils.getOrganizationId(mContext);

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<JosData> call = service.josbyclient(token, client);
        call.enqueue(new Callback<JosData>() {
            @Override
            public void onResponse(Call<JosData> call, Response<JosData> response) {
                final int status = response.code();
                mProgressBar.setVisibility(View.GONE);
                if( response.isSuccessful() ){
                    JosData data = response.body();
                    mListJos = data.getJos();
                    mJosAdapter = new JosAdapter(mContext, mListJos, getCallbacks());
                    mRecyclerView.setAdapter(mJosAdapter);
                    mRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());

                    final boolean isempty = mListJos.size()<=0;
                    updateEmptyView(isempty);

                }else{
                    LOGE(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<JosData> call, Throwable t) {
                LOGE("onFailure", t.getMessage().toString());
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void updateEmptyView(boolean isempty){
        final boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);

        mRecyclerView.setVisibility( isempty? View.GONE : View.VISIBLE );
        mControlPanelContainer.setVisibility(isempty? View.GONE: View.VISIBLE);
        mViewEmptyContainer.setVisibility(isempty? View.VISIBLE : View.GONE);
        mViewNoticeContainer.setVisibility(userHasLogin ? View.GONE : View.VISIBLE);
    }

    private JosItemViewHolder.Callbacks getCallbacks(){
        return  this;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initState();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Activity activity = getActivity();
        switch (item.getItemId()) {
            case R.id.menu_setting :
                SettingsActivity.startSettingsActivity(activity);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initState(){
        final boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);

        mViewEmptyContainer.setVisibility(View.GONE);
        mViewNoticeContainer.setVisibility(userHasLogin ? View.GONE: View.VISIBLE);
        mRecyclerView.setVisibility(userHasLogin ? View.VISIBLE : View.GONE);

        if(userHasLogin){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadJobOrder();
                }
            }, HANDLER_LAUNCH_DELAY);
        }


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        sp.registerOnSharedPreferenceChangeListener(mSettingsChangeListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mSettingsChangeListener != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
            sp.unregisterOnSharedPreferenceChangeListener(mSettingsChangeListener);
        }
    }

    @Override
    public void onJosClicked(Jos jos) {
        //TODO
    }


}
