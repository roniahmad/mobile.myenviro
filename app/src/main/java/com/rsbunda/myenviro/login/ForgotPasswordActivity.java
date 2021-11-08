package com.rsbunda.myenviro.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.response.BaseResponse;
import com.rsbunda.myenviro.ui.BaseActivity;
import com.rsbunda.myenviro.util.AlertUtils;
import com.rsbunda.myenviro.util.LoginUtils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity {

    private EditText mEdUserEmail;
    private Button mBtnForgotPassword;
    private ProgressBar mProgressBar;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);
        mContext = getApplicationContext();

        initViews();
        initListeners();
        initState();
        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(ForgotPasswordActivity.this);
            }
        });
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void initState() {
        final boolean usingLastId = LoginUtils.isUsingLastUserName(mContext);
        final String lastId = LoginUtils.getLastUserName(mContext);

        if(usingLastId && !StringUtils.isEmpty(lastId)){
            mEdUserEmail.setText(lastId);
        }
    }

    private void initViews(){
        mEdUserEmail = (EditText) findViewById(R.id.editTextEmail);
        mBtnForgotPassword = (Button) findViewById(R.id.forgot_link);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    private void initListeners(){
        mBtnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
    }

    public void onLoginClick(View v){
        LoginActivity.startLoginActivity(this);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }



    private void forgotPassword(){
        mProgressBar.setVisibility(View.VISIBLE);

        final String token = LoginUtils.getUserToken(this);
        final String email = mEdUserEmail.getText().toString();

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<BaseResponse> call = service.resetpwd(token, email);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                final int status = response.code();

                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    final int success = response.body().getSuccess();
                    final String message = response.body().getMessage();
                    final String title = (success==1) ?  getApplicationContext().getString(R.string.information):
                            getApplicationContext().getString(R.string.warning);

                    Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                    if(success==1){
                        finish();
                    }else{
                        AlertUtils.infoDialog(getApplicationContext(), title, "<b>"+message+ "</b>");
                        clearInput();
                    }
                }else{
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseResponse>() {}.getType();
                    BaseResponse errorResponse = gson.fromJson(response.errorBody().charStream(),type);

                    Toast.makeText(ForgotPasswordActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    clearInput();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                clearInput();
            }
        });

    }


    private void clearInput(){
        mEdUserEmail.getText().clear();
    }

    public static void startForgotPasswordActivity(final Context activity) {
        Intent resetPasswordIntent = new Intent(activity,
                ForgotPasswordActivity.class);
        activity.startActivity(resetPasswordIntent);
    }

}
