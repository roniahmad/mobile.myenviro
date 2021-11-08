package com.rsbunda.myenviro.manpower;


import static com.rsbunda.myenviro.constant.CommonConstant.CONST_JOB;
import static com.rsbunda.myenviro.constant.CommonConstant.CONST_JOB_ID;
import static com.rsbunda.myenviro.constant.CommonConstant.CONST_JOS_ID;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.dac.DacActivity;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.model.sales.ManPowerDetil;
import com.rsbunda.myenviro.io.model.sales.ManPowerDetilData;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.UIUtils;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManPowerFragment extends Fragment
    implements ManPowerItemViewHolder.Callbacks{

    private static final String TAG = makeLogTag(ManPowerFragment.class);

    //Delay to update user information
    private static final int HANDLER_LAUNCH_DELAY = 300;

    private Handler mHandler = new Handler();

    private List<ManPowerDetil> mListJmpd;
    private RecyclerView mRecyclerView;
    private ManPowerAdapter mViewAdapter;

    private ProgressBar mProgressBar;

    private View mEmptyContainer;

    private TextView mTxtEmpty;

    private Context mContext;

    private int mJosId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manpower_fragment, container, false);

        mContext = getContext();

        initViews(view);
        initListeners();

        return view;
    }

    private void initViews(final View view){
        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(mContext.getDrawable(R.drawable.divider));
        mRecyclerView.addItemDecoration(divider);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mEmptyContainer = (View) view.findViewById(R.id.empty_container);

        mTxtEmpty = (TextView) view.findViewById(R.id.tv_empty);
        UIUtils.setTextMaybeHtml(mTxtEmpty, getString(R.string.empty_jmpd));

        mListJmpd = Collections.EMPTY_LIST;
    }

    private  void initListeners(){
        //TODO
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();
        mJosId = bundle.getInt(CONST_JOS_ID);
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

    //check state of user
    private void initState(){

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadTeams();
            }
        }, HANDLER_LAUNCH_DELAY);

    }

    private void loadTeams(){
        mProgressBar.setVisibility(View.VISIBLE);
        mListJmpd.clear();

        final String token = "Bearer "+ LoginUtils.getUserToken(mContext);

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<ManPowerDetilData> call = service.manpowerbyjos(token, mJosId);
        call.enqueue(new Callback<ManPowerDetilData>() {
            @Override
            public void onResponse(Call<ManPowerDetilData> call, Response<ManPowerDetilData> response) {
                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    ManPowerDetilData data = response.body();
                    mListJmpd = data.getManPowerDetils();
                    mViewAdapter = new ManPowerAdapter(mContext, mListJmpd, getCallbacks());
                    mRecyclerView.setAdapter(mViewAdapter);
                    final boolean isEmpty = mListJmpd.size()<=0;
                    updateEmptyView(isEmpty);

                }else{
                    LOGE(TAG, response.toString());
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ManPowerDetilData> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                LOGE(TAG, t.toString());
            }
        });


    }

    private void updateEmptyView(boolean isempty){
        mRecyclerView.setVisibility(isempty? View.GONE : View.VISIBLE );
        mEmptyContainer.setVisibility(isempty? View.VISIBLE : View.GONE);
    }

    private ManPowerItemViewHolder.Callbacks getCallbacks(){
        return  this;
    }

    @Override
    public void onTeamsClicked(ManPowerDetil man) {
        final ManPowerDetil m = man;
        final int mId = m.getId();
        final int mJabatanId = m.getJabatanId();
        final String jabatan = m.getJabatan();

        Intent dacIntent = new Intent(mContext, DacActivity.class);
        dacIntent.putExtra(CONST_JOS_ID, mJosId);
        dacIntent.putExtra(CONST_JOB_ID, mJabatanId);
        dacIntent.putExtra(CONST_JOB, jabatan);

        mContext.startActivity(dacIntent);

    }
}
