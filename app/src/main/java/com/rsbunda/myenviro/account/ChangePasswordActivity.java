package com.rsbunda.myenviro.account;

import android.app.Activity;
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
import com.rsbunda.myenviro.io.response.ChangePasswordResponse;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.ui.BaseActivity;
import com.rsbunda.myenviro.util.LoginUtils;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class ChangePasswordActivity extends BaseActivity {

    private static final String TAG = makeLogTag(ChangePasswordActivity.class);

    private static final Integer SUCCESS = 1;

    private EditText mEdOldPassword;
    private EditText mEdNewPassword;
    private EditText mEdRetypePassword;

    private Button mBtnChangePassword;

    private ProgressBar mProgressBar;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        mContext = getApplicationContext();

        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(ChangePasswordActivity.this);
            }
        });

        initViews();
        initListeners();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, AccountActivity.class);
    }

    private void initViews(){
        mEdOldPassword = (EditText) findViewById(R.id.user_old_password);
        mEdNewPassword = (EditText) findViewById(R.id.user_new_password);
        mEdRetypePassword = (EditText) findViewById(R.id.user_retype_new_password);

        mBtnChangePassword = (Button) findViewById(R.id.change_password_link);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    private void initListeners(){
        mBtnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirm()){
                    changePassword();
                }else{
                    Toast.makeText(ChangePasswordActivity.this, getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
                    mEdNewPassword.setText("");
                    mEdRetypePassword.setText("");
                }
            }



        });
    }

    private boolean confirm(){
        boolean result;
        final String newpass = mEdNewPassword.getText().toString();
        final String retypepass = mEdRetypePassword.getText().toString();

        result = newpass.equals(retypepass);
        return result;
    }

    private void changePassword(){
        mProgressBar.setVisibility(View.VISIBLE);

        final String token = LoginUtils.getUserToken(mContext);
        final String oldpass = mEdOldPassword.getText().toString();
        final String newpass = mEdNewPassword.getText().toString();
        final String retypepass = mEdRetypePassword.getText().toString();

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<ChangePasswordResponse> call = service.changepassword(token, oldpass, newpass, retypepass);
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                final int status = response.code();

                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    final int success = response.body().getSuccess();
                    final String message = response.body().getMessage();
                    final String title = mContext.getString(R.string.warning);

                    LoginUtils.setUserPassword(mContext, newpass);

                    Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                    if(success==SUCCESS){
                        finish();
                    }else{
//                        AlertUtils.infoDialog(ctx, title, message);
                        clearInput();
                    }

                }else{
                    Gson gson = new Gson();
                    Type type = new TypeToken<ChangePasswordResponse>() {}.getType();
                    ChangePasswordResponse errorResponse = gson.fromJson(response.errorBody().charStream(),type);

                    Toast.makeText(ChangePasswordActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    clearInput();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                clearInput();
            }
        });
    }

    private void clearInput(){
        mEdOldPassword.getText().clear();
        mEdNewPassword.getText().clear();
        mEdRetypePassword.getText().clear();
        mEdOldPassword.requestFocus();
    }

    public static void startChangePasswordActivity(final Activity activity) {
        Intent intent = new Intent(activity,
                ChangePasswordActivity.class);
        activity.startActivity(intent);
    }

}
