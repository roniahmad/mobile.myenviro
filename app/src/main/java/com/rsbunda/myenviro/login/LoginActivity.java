package com.rsbunda.myenviro.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.account.AccountActivity;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.model.Hero;
import com.rsbunda.myenviro.io.model.Token;
import com.rsbunda.myenviro.io.response.LoginResponse;
import com.rsbunda.myenviro.io.model.Organization;
import com.rsbunda.myenviro.ui.BaseActivity;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.ServerConfigUtils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class LoginActivity extends BaseActivity
        implements View.OnClickListener {

    private static final String TAG = makeLogTag(LoginActivity.class);

    private View mView;

    private EditText mEdEmail;
    private EditText mEdPassword;
    private Button mBtnLogin;
    private Button mButtonForgot;
    private Button mButtonSignUp;

    private ProgressBar mProgressBar;

    private static final int SUCCESS = 1;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mView = findViewById(R.id.login_container);
        mContext = this.getApplicationContext();

        initViews();
        initListeners();
        initState();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, AccountActivity.class);
    }

    private void initViews(){
        mEdEmail = (EditText) mView.findViewById(R.id.input_email);
        mEdPassword = (EditText) mView.findViewById(R.id.input_password);

        mBtnLogin = (Button) mView.findViewById(R.id.login_link);
        mButtonForgot = (Button) mView.findViewById(R.id.link_forgot);
        mButtonSignUp = (Button) mView.findViewById(R.id.link_signup);

        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_bar);
    }

    private void initListeners() {
        mBtnLogin.setOnClickListener(this);
        mButtonForgot.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
    }

    private void initState(){
        final boolean usingLastId = LoginUtils.isUsingLastUserName(mContext);
        final String lastId = LoginUtils.getLastUserName(mContext);

        if(usingLastId && !StringUtils.isEmpty(lastId)){
            mEdEmail.setText(lastId);
        }
    }

    public static void startLoginActivity(final Context activity) {
        Intent loginIntent = new Intent(activity,
                LoginActivity.class);
        activity.startActivity(loginIntent);
    }

    @Override
    public void onClick(View v) {
        if(v==mBtnLogin){
            login();
        }else if(v == mButtonForgot){
            ForgotPasswordActivity.startForgotPasswordActivity(this);
            finish();
        }else if(v == mButtonSignUp){
            RegisterAccountActivity.startRegisterAccountActivity(this);
            finish();
        }
    }

    private boolean validate(){
        boolean valid = true;

        String email = mEdEmail.getText().toString();
        String password = mEdPassword.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEdEmail.setError(getString(R.string.login_failed_text_email));
            mEdEmail.requestFocus();
            valid = false;
        } else {
            mEdEmail.setError(null);
        }

        if (password.isEmpty()) {
            mEdPassword.setError(getString(R.string.login_failed_text_password));
            mEdPassword.requestFocus();
            valid = false;
        } else {
            mEdPassword.setError(null);
        }

        return valid;
    }

    private void login(){
        LOGD(TAG, "Login");
        if(!validate()){
            return;
        }

        loginMeIn();
    }

    private void loginMeIn(){
        mProgressBar.setVisibility(View.VISIBLE);

        final String email = mEdEmail.getText().toString();
        final String password = mEdPassword.getText().toString();

        HalloService service = HalloGenerator.createService(HalloService.class, mContext);
        Call<LoginResponse> call = service.login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                final int responCode = response.code();

                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    final int success = response.body().getSuccess();
                    final String message = response.body().getMessage();

                    final Token token = response.body().getToken();

                    final Organization org = response.body().getOrg();

                    final Hero hero = response.body().getHero();


                    onLoginSuccess(token, org, hero, password);

                }else{

                    try {

                        Gson gson = new Gson();
                        Type type = new TypeToken<LoginResponse>() {}.getType();
                        LoginResponse errorResponse = gson.fromJson(response.errorBody().charStream(),type);
                        LOGE(TAG, errorResponse.getMessage() );
                        Toast.makeText(LoginActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                    if (responCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT){
                        Toast.makeText(getApplicationContext(), getString(R.string.err_socket_timeout), Toast.LENGTH_SHORT).show();
                    }else if (responCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                        Toast.makeText(getApplicationContext(), getString(R.string.err_unauthorized), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    cleanUpInput();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //Transport level errors such as no internet etc.
                mProgressBar.setVisibility(View.GONE);
                onLoginFailed();
                LOGE(TAG, t.toString());
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_socket_timeout), Toast.LENGTH_SHORT).show();
                    LOGE(TAG, getString(R.string.err_socket_timeout));
                }
                cleanUpInput();
            }
        });
    }

    private void cleanUpInput(){
        mEdEmail.getText().clear();
        mEdPassword.getText().clear();
        mEdEmail.requestFocus();
    }

    public void onLoginSuccess(Token token, Organization org,
                               Hero hero, String password) {

        LoginUtils.markUserHasLogin(mContext, true);
        LoginUtils.setUserToken(mContext, token.getToken());
        LoginUtils.setUserName(mContext, hero.getUsername());
        LoginUtils.setUserPassword(mContext, password);
        LoginUtils.setLastUserName(mContext, hero.getUsername());

        LoginUtils.markUserHasValidated(mContext, (hero.getVerified()==1) ? true: false );

        if(org != null){
            LoginUtils.setOrganizationId(mContext, org.getUuid());
            LoginUtils.setOrganizationName(mContext, org.getPerusahaan());
//            LoginUtils.setOrganizationLogo(mContext, org.getAvatarUrl());
//            LoginUtils.setOrganizationUnitAddress(mContext, org.getOrganizationAddress());
//            LoginUtils.setPrefOrganizationUnitCity(mContext, org.getOrganizationCity());
            LoginUtils.setOrganizationCode(mContext, org.getKode());

            //store latest org
            LoginUtils.setOrganizationIdLatest(mContext, org.getUuid());
        }

        //set hero information by login
        if(hero != null) {
            LoginUtils.setHeroNip(mContext, hero.getNip());
            LoginUtils.setHeroName(mContext, hero.getNama());
            LoginUtils.setHeroGelarDepan(mContext, hero.getGelarDepan());
            LoginUtils.setHeroGelarBelakang(mContext, hero.getGelarBelakang());
            LoginUtils.setHeroTipeUser(mContext, hero.getTipeUser());
            LoginUtils.setHeroPegawaiId(mContext, hero.getPegawaiId());
        }

//        mBtnLogin.setEnabled(true);
        finish();
    }

    /**
     * Clear all configuration
     * */
    public void onLoginFailed() {

        LoginUtils.markUserHasLogin(mContext, false);
        LoginUtils.setUserToken(mContext, null);
        LoginUtils.setUserName(mContext, null);
        LoginUtils.setUserPassword(mContext, null);
        LoginUtils.markUserHasValidated(mContext, false);

        LoginUtils.setOrganizationId(mContext, null);
        LoginUtils.setOrganizationName(mContext, null);
        LoginUtils.setOrganizationLogo(mContext, null);
        LoginUtils.setOrganizationUnitAddress(mContext, null);
        LoginUtils.setPrefOrganizationUnitCity(mContext, null);
        LoginUtils.setOrganizationCode(mContext, null);

        LoginUtils.setHeroNip(mContext, null);
        LoginUtils.setHeroName(mContext, null);
        LoginUtils.setHeroGelarDepan(mContext, null);
        LoginUtils.setHeroGelarBelakang(mContext, null);
        LoginUtils.setPxBpjsId(mContext, null);
        LoginUtils.setPxLastVisitedClinic(mContext, null);
        LoginUtils.setPxLastVisit(mContext, 0l);

        ServerConfigUtils.setMinRegistrationDays(mContext, 1);

        LoginUtils.setPxActiveTicket(mContext, 0);
        LoginUtils.setBpjsEnabled(mContext, null);
        LoginUtils.setPathDoctor(mContext, null);
        LoginUtils.setPathPoly(mContext, null);

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_failed_text),
                Toast.LENGTH_LONG).show();

        mBtnLogin.setEnabled(true);
    }

}
