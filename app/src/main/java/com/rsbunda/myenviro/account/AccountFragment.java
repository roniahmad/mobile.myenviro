package com.rsbunda.myenviro.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rsbunda.myenviro.BuildConfig;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.about.AboutActivity;
import com.rsbunda.myenviro.home.ProfileActivity;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.response.HalloResponse;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.login.LoginActivity;
import com.rsbunda.myenviro.login.RegisterAccountActivity;
import com.rsbunda.myenviro.settings.SettingsActivity;
import com.rsbunda.myenviro.ui.widget.BezelImageView;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.UIUtils;

import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class AccountFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = makeLogTag(AccountFragment.class);

    private ProgressDialog mProgressLogin;

    //Delay to update user information
    private static final int HANDLER_LAUNCH_DELAY = 300;

    private Handler mHandler = new Handler();

    private Button mBtnLogin;
    private Button mBtnRegister;
    private Button mBtnLogout;
    private Button mBtnChangePassword;

    private TextView mHeroUserName;
    private TextView mHeroNip;

    private View mViewUserAccountBlock;
    private View mViewUserLoginBlock;

    private BezelImageView mImageProfile;

    private Context mContext;

    private SharedPreferences.OnSharedPreferenceChangeListener mSettingsChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                      String key) {
                    if(LoginUtils.PREF_USER_HAS_LOGIN.equals(key)){
                        final boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);

                        mViewUserAccountBlock.setVisibility(userHasLogin? View.VISIBLE : View.GONE);
                        mBtnLogout.setVisibility(userHasLogin ? View.VISIBLE : View.GONE);
                        mBtnChangePassword.setVisibility(userHasLogin ? View.VISIBLE : View.GONE );

                        mViewUserLoginBlock.setVisibility(userHasLogin ? View.GONE : View.VISIBLE);

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showUserProfile();
                            }
                        }, HANDLER_LAUNCH_DELAY);
                    }
                }
    };

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initListener(view);
        initState();

    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
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
    public void onClick(View view) {
        final Activity activity  = getActivity();

        int id = view.getId();

        if (id == R.id.login_link) {
            LoginActivity.startLoginActivity(activity);
        }else if (id==R.id.register_link){
            RegisterAccountActivity.startRegisterAccountActivity(activity);
        }else if (id == R.id.logout_link) {
            logMeOut();
        }else if (id == R.id.change_password_link) {
            ChangePasswordActivity.startChangePasswordActivity(activity);
        }else if (id==R.id.recommend_link){
            shareMyApp();
        }else if (id == R.id.about_link) {
            AboutActivity.startAboutActivity(activity);
        }else if (id==R.id.user_profile){
            ProfileActivity.startProfileActivity(activity);
        }else if (id == R.id.rate_this_app_link) {
            rateMyApp();
        }
//        else if(id == R.id.reset_link){
//            reset();
//        }
    }

    private void initViews(final View view){
        mImageProfile = (BezelImageView) view.findViewById(R.id.user_profile);

        // Login
        mBtnLogin = (Button) view.findViewById(R.id.login_link);
        mBtnRegister = (Button) view.findViewById(R.id.register_link);
        //Logout
        mBtnLogout = (Button) view.findViewById(R.id.logout_link);
        //change Password
        mBtnChangePassword = (Button) view.findViewById(R.id.change_password_link);

        //User Profile Information
        mHeroUserName = (TextView) view.findViewById(R.id.user_name);
        mHeroNip = (TextView) view.findViewById(R.id.user_address);

        mViewUserAccountBlock = view.findViewById(R.id.user_account_block);
        mViewUserLoginBlock = view.findViewById(R.id.user_login_block);

        TextView mTxtDeveloper = (TextView) view.findViewById(R.id.about_developer);
        UIUtils.setTextMaybeHtml(mTxtDeveloper, mContext.getString(R.string.about_copyrights));

        // Version
        TextView mTxtAppVersion = (TextView) view.findViewById(R.id.tv_app_version);
        UIUtils.setTextMaybeHtml(mTxtAppVersion, mContext.getResources()
                .getString(R.string.about_main, BuildConfig.VERSION_NAME));
    }

    private void initListener(final View view){
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mBtnLogout.setOnClickListener(this);
        mBtnChangePassword.setOnClickListener(this);
        mImageProfile.setOnClickListener(this);

        //TODO: reset, for development purpose
//        view.findViewById(R.id.reset_link).setOnClickListener(this);

        //recommend us
        view.findViewById(R.id.recommend_link).setOnClickListener(this);
        //about
        view.findViewById(R.id.about_link).setOnClickListener(this);
        //rate
        view.findViewById(R.id.rate_this_app_link).setOnClickListener(this);
    }

    private void showUserProfile(){
        final boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);
        final boolean userHasValidated = LoginUtils.hasUserValidated(mContext);

        String userLoggedIn = "" ;
        String userCompany = "";

        if(userHasValidated){
            userLoggedIn = ( userHasLogin ?  (StringUtils.isEmpty(LoginUtils.getHeroName(mContext)) ? mContext.getString(R.string.anonymous): LoginUtils.getHeroName(mContext) ) : getString(R.string.anonymous)) ;
            userCompany = ( userHasLogin ? (StringUtils.isEmpty(LoginUtils.getOrganizationName(mContext))? mContext.getString(R.string.profile_address_na):LoginUtils.getOrganizationName(mContext)) : getString(R.string.profile_address_na)) ;
        }

        final int userPic = (userHasLogin? R.drawable.anonymous : R.drawable.anonymous);

        mImageProfile.setImageResource(userPic);
        mHeroUserName.setText(userLoggedIn);
        mHeroNip.setText(userCompany);
    }

    private void rateMyApp(){

        final String packageName = getActivity().getApplicationContext().getPackageName();

        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT | 
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void shareMyApp(){
        try {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.app_name));
            String shareMessage = "\n"+ mContext.getResources().getString(R.string.share_header) +"\n\n";
            shareMessage += "\n"+ mContext.getResources().getString(R.string.share_download_at) +"\n\n";
            shareMessage += "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, mContext.getResources().getString(R.string.title_share)));

        } catch(Exception e) {
            //e.toString();
        }
    }

    private void initState() {
        boolean userHasLogin = LoginUtils.isUserHasLogin(mContext);

        mViewUserAccountBlock.setVisibility(userHasLogin? View.VISIBLE : View.GONE);
        mBtnLogout.setVisibility(userHasLogin ? View.VISIBLE : View.GONE);
        mBtnChangePassword.setVisibility(userHasLogin ? View.VISIBLE : View.GONE);

        mViewUserLoginBlock.setVisibility(userHasLogin ? View.GONE : View.VISIBLE);

        showUserProfile();
    }

    private void forceLogout(){
        onLogoutSuccess();
    }

//    private void reset(){
//        LoginUtils.markUserHasLogin(getContext(), false);
//        LoginUtils.setUserToken(getContext(), "");
//        LoginUtils.setUserName(getContext(), "");
//
//        LoginUtils.setPxIdrm(getContext(), null);
//        LoginUtils.setPxName(getContext(), null);
//        LoginUtils.setPxAddress(getContext(), null);
//        LoginUtils.setPxLastVisit(getContext(), 0l);
//        LoginUtils.setPxLastVisitedClinic(getContext(), null);
//
//        WelcomeUtils.markTosAccepted(getContext(), false);
//
//        Toast.makeText(getContext(), "Reset OK",
//                Toast.LENGTH_LONG).show();
//    }

    private void logMeOut(){
        mProgressLogin = new ProgressDialog(mContext, R.style.Theme_HalloBunda_AlertDialog);
        mProgressLogin.setIndeterminate(true);
        mProgressLogin.setMessage(mContext.getResources().getString(R.string.logging_out));

        showProgressDialog();
        final String token = "Bearer "+LoginUtils.getUserToken(mContext);

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<HalloResponse> call = service.logout(token);
        call.enqueue(new Callback<HalloResponse>() {
            @Override
            public void onResponse(Call<HalloResponse> call, Response<HalloResponse> response) {
                final int responCode = response.code();
                hideProgressDialog();

                if(response.isSuccessful()){
                    onLogoutSuccess();
                }else{
                    LOGE(TAG, response.toString() );
                    if (responCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                        forceLogout();
                    }else{
                        onLogoutFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<HalloResponse> call, Throwable throwable) {
                hideProgressDialog();
                forceLogout();
                onLogoutSuccess();
//                onLogoutFailed();
                LOGE(TAG, throwable.toString());
            }
        });
    }

    public void onLogoutSuccess() {

        LoginUtils.unAuthorizedUser(mContext);

        Toast.makeText(mContext, mContext.getResources().getString(R.string.logout_success),
                Toast.LENGTH_LONG).show();
    }

    public void onLogoutFailed() {
        Toast.makeText(mContext, mContext.getResources().getString(R.string.logout_failed),
                Toast.LENGTH_LONG).show();
    }

    private void showProgressDialog(){
        if(!mProgressLogin.isShowing()){
            mProgressLogin.show();
        }
    }

    private void hideProgressDialog(){
        if(mProgressLogin.isShowing()){
            mProgressLogin.dismiss();
        }
    }
}