package com.rsbunda.myenviro.home;

import android.app.Activity;
import android.content.Context;
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
import com.rsbunda.myenviro.home.service.ServiceItemViewHolder;
import com.rsbunda.myenviro.home.service.ServicesAdapter;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.model.layanan.Layanan;
import com.rsbunda.myenviro.io.model.layanan.LayananData;
import com.rsbunda.myenviro.io.model.layanan.ProductLayanan;
import com.rsbunda.myenviro.io.model.layanan.ProductLayananData;
import com.rsbunda.myenviro.login.LoginActivity;
import com.rsbunda.myenviro.login.RegisterAccountActivity;
import com.rsbunda.myenviro.util.LoginUtils;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment
    implements ServiceItemViewHolder.Callbacks{

    private static final String TAG = makeLogTag(HomeFragment.class);

    //Delay to update user information
    private static final int HANDLER_LAUNCH_DELAY = 300;

    private Handler mHandler = new Handler();
    private RecyclerView mRecyclerView;
    private ServicesAdapter mServiceAdapter;

    private List<Layanan> mListLayanan;
    private List<ProductLayanan> mListProductLayanan;

    private ProgressBar mProgressBar;

    private View mUserContainer;
    private View mNoticeContainer;
    private View mViewEmptyContainer;

    private TextView mTxtHero;
    private TextView mTxtHeroCompany;

//    private Button mBtnCancel;
//    private Button mBtnProfile;
//    private Button mBtnScan;

    private Button mBtnLogin;
    private Button mBtnRegister;

    private Context mContext;

    private StringBuilder mBuffer = new StringBuilder();

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

                    }
                }
            };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initListeners();
        initState();
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


    private void initViews(final View v){
        mRecyclerView = (RecyclerView) v.findViewById(android.R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerDecoration(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mNoticeContainer = v.findViewById(R.id.notice_container);
        mUserContainer = v.findViewById(R.id.user_container);
        mViewEmptyContainer = v.findViewById(R.id.empty_container);

        mNoticeContainer.setVisibility(View.GONE);
        mUserContainer.setVisibility(View.GONE);
        mViewEmptyContainer.setVisibility(View.GONE);

        mTxtHero = (TextView) v.findViewById(R.id.hero_name);
        mTxtHeroCompany = (TextView) v.findViewById(R.id.hero_nip);

        mBtnLogin = (Button) v.findViewById(R.id.login_link);
        mBtnRegister = (Button) v.findViewById(R.id.register_link);

        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        mListLayanan = Collections.EMPTY_LIST;
        mListProductLayanan = Collections.EMPTY_LIST;

    }

    private void initListeners(){
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccountActivity.startRegisterAccountActivity(getActivity());
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startLoginActivity(getActivity());
            }
        });

    }

    private ServiceItemViewHolder.Callbacks getCallbacks(){
        return  this;
    }

    private void loadServices(){
        mProgressBar.setVisibility(View.VISIBLE);
        mListLayanan.clear();

        final String token = LoginUtils.getUserToken(mContext);

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<LayananData> call = service.layanan(token);
        call.enqueue(new Callback<LayananData>() {
            @Override
            public void onResponse(Call<LayananData> call, Response<LayananData> response) {
                final int status = response.code();
                mProgressBar.setVisibility(View.GONE);
                if( response.isSuccessful() ){
                    LayananData data = response.body();
                    mListLayanan = data.getLayanan();
                    mServiceAdapter = new ServicesAdapter(mContext, mListLayanan, mListProductLayanan, getCallbacks());
                    mRecyclerView.setAdapter(mServiceAdapter);
                    final boolean isempty = mListLayanan.size()<=0;
                    updateEmptyView(isempty);
                }else{
                    LOGE(TAG, response.toString());

                }
            }

            @Override
            public void onFailure(Call<LayananData> call, Throwable t) {
                LOGE("onFailure", t.getMessage().toString());
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void loadProductServices(){
        mProgressBar.setVisibility(View.VISIBLE);
        mListProductLayanan.clear();

        final String token = LoginUtils.getUserToken(mContext);

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<ProductLayananData> call = service.productlayanan(token);
        call.enqueue(new Callback<ProductLayananData>() {
            @Override
            public void onResponse(Call<ProductLayananData> call, Response<ProductLayananData> response) {
                final int status = response.code();
                mProgressBar.setVisibility(View.GONE);
                if( response.isSuccessful() ){
                    ProductLayananData data = response.body();
                    mListProductLayanan = data.getLayanan();

                    if(mListProductLayanan.size()>0){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadServices();
                            }
                        }, HANDLER_LAUNCH_DELAY);
                    }

                }else{
                    LOGE(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ProductLayananData> call, Throwable t) {
                LOGE("onFailure", t.getMessage().toString());
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initState() {
        boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);
        displayUserLogedIn(userHasLogin);

        mNoticeContainer.setVisibility(userHasLogin ? View.GONE : View.VISIBLE);
        mUserContainer.setVisibility(userHasLogin? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(userHasLogin ? View.VISIBLE : View.GONE);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadProductServices();
            }
        }, HANDLER_LAUNCH_DELAY);

    }

    private void displayUserLogedIn(boolean haslogin){
        if(haslogin){
            mTxtHero.setText(LoginUtils.getHeroName(mContext));
            mTxtHeroCompany.setText(LoginUtils.getOrganizationName(mContext));
        }else {
            mTxtHero.setText("");
            mTxtHeroCompany.setText("");
        }
    }

    private void updateEmptyView(boolean isEmpty){
        final boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);

        mRecyclerView.setVisibility( isEmpty? View.GONE : View.VISIBLE );
        mViewEmptyContainer.setVisibility(isEmpty? View.VISIBLE : View.GONE);
        mNoticeContainer.setVisibility(userHasLogin ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onServicesClicked(Layanan service) {
        Toast.makeText(mContext, mContext.getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
    }
}
