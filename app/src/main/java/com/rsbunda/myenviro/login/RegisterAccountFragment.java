package com.rsbunda.myenviro.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.response.PxRegNewUserResponse;
import com.rsbunda.myenviro.io.model.Organization;
import com.rsbunda.myenviro.io.model.Patient;
import com.rsbunda.myenviro.util.AlertUtils;
import com.rsbunda.myenviro.util.LogUtils;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.ServerConfigUtils;
import com.rsbunda.myenviro.util.UIUtils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsbunda.myenviro.util.LogUtils.LOGE;

public class RegisterAccountFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(RegisterAccountFragment.class);

    private static final Integer SUCCESS = 1;

    private EditText mEdUserName;
    private EditText mEdEmail;
    private EditText mEdPassword;
    private EditText mEdRetypePassword;

    private CheckBox mChkTerm;
    private TextView mTvAgree;

    private Button mBtnRegisterLink;
    private Button mBtnLoginLink;

    private ProgressBar mProgressBar;


    private Context mContext;

    private Boolean isValidEntry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        return inflater.inflate(R.layout.register_account_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initListeners();
    }

    public static RegisterAccountFragment getInstance(){
        RegisterAccountFragment fragment = new RegisterAccountFragment();
        return fragment;
    }


    private void initListeners() {
        mChkTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                mBtnRegisterLink.setEnabled(checked);
            }
        });

        mBtnRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        mBtnLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.startLoginActivity(mContext);
                getActivity().finish();
            }
        });

    }

    private void validatePxEntry(){
        if(StringUtils.isEmpty(mEdUserName.getText().toString())){
            mEdUserName.setError(mContext.getString(R.string.pxreg_acc_username_required));
            mEdUserName.requestFocus();
            isValidEntry = false;
            disableRegister();
            return;
        }else if(StringUtils.isEmpty(mEdEmail.getText().toString())){
            mEdEmail.setError(mContext.getString(R.string.pxreg_acc_username_required));
            mEdEmail.requestFocus();
            isValidEntry = false;
            disableRegister();
            return;
        }else if(StringUtils.isEmpty(mEdPassword.getText().toString())){
            mEdPassword.setError(mContext.getString(R.string.pxreg_acc_password_required));
            mEdPassword.requestFocus();
            isValidEntry = false;
            disableRegister();
            return;
        }else if(StringUtils.isEmpty(mEdRetypePassword.getText().toString())) {
            mEdRetypePassword.setError(mContext.getString(R.string.pxreg_acc_retype_password_required));
            mEdRetypePassword.requestFocus();
            isValidEntry = false;
            disableRegister();
            return;
        }
        isValidEntry = true;

    }


    private void disableRegister(){
        mChkTerm.setChecked(false);
        mBtnRegisterLink.setEnabled(false);
    }

    private void register(){
        validatePxEntry();

        if(!isValidEntry) return;

        if (!confirmPassword()) {

            Toast.makeText(mContext, mContext.getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();

            mEdPassword.setText("");
            mEdRetypePassword.setText("");
            mEdPassword.setError(mContext.getString(R.string.password_not_match));
            mEdPassword.requestFocus();

            disableRegister();
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mBtnRegisterLink.setEnabled(false);

        final String token = LoginUtils.getUserToken(mContext);

        final String name = mEdUserName.getText().toString();
        final String email = mEdEmail.getText().toString();
        final String pass = mEdPassword.getText().toString();

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<PxRegNewUserResponse> call = service.pxregnewuser(name, email,pass);
        call.enqueue(new Callback<PxRegNewUserResponse>() {
            @Override
            public void onResponse(Call<PxRegNewUserResponse> call, Response<PxRegNewUserResponse> response) {
                final int status = response.code();

                if(response.isSuccessful()) {

                    final int success = response.body().getSuccess();
                    final String message = response.body().getMessage();
                    final int isValidated = response.body().getIsvalidated();
                    final String newToken = response.body().getToken();
                    final Organization org = response.body().getOrg();
                    final int mindays = response.body().getMindays();
                    final Patient px = response.body().getPatient();
                    final String warning = mContext.getString(R.string.warning);
                    final String information = mContext.getString(R.string.information);

                    String validate_message="";
                    if(success==SUCCESS){
                        LoginUtils.markUserHasLogin(mContext, true);
                        LoginUtils.setUserToken(mContext, newToken);
                        LoginUtils.setUserName(mContext, email);
                        LoginUtils.setUserPassword(mContext, pass);

                        LoginUtils.setOrganizationId(mContext, org.getUuid());
                        LoginUtils.setOrganizationName(mContext, org.getPerusahaan());
//                        LoginUtils.setOrganizationLogo(mContext, org.getAvatarUrl());
//                        LoginUtils.setOrganizationUnitAddress(mContext, org.getOrganizationAddress());
//                        LoginUtils.setPrefOrganizationUnitCity(mContext, org.getOrganizationCity());

                        //store latest org
                        LoginUtils.setOrganizationIdLatest(mContext, org.getUuid());

                        //set px information by login
                        if(px!=null){
                            LoginUtils.setHeroNip(mContext, px.getIdrm());
                            LoginUtils.setHeroName(mContext, px.getNama());
                            LoginUtils.setHeroGelarDepan(mContext, px.getAlamat());
                        }

                        ServerConfigUtils.setMinRegistrationDays(mContext, mindays);

                        LoginUtils.markUserHasValidated(mContext, isValidated==0? false: true);
                        LoginUtils.setPxLastTempPassword(mContext, pass);
                        LoginUtils.markUserHasChangePassword(mContext, false);

                        if(isValidated==0){
                            validate_message = String.format(mContext.getString(R.string.register_user_validate_failed), org.getPerusahaan(), name);
                        }else{
                            validate_message = String.format(mContext.getString(R.string.register_user_validate_ok), org.getPerusahaan(), name);
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(mContext.getString(R.string.information));
                        builder.setMessage(Html.fromHtml(validate_message));
                        builder.setCancelable(false);
                        builder.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        });
                        builder.show();

                    }else{

                        Gson gson = new Gson();
                        Type type = new TypeToken<PxRegNewUserResponse>() {}.getType();
                        PxRegNewUserResponse errorResponse = gson.fromJson(response.errorBody().charStream(),type);

                        AlertUtils.infoDialog(mContext, getString(R.string.warning),
                                errorResponse.getMessage());
                    }

                    mProgressBar.setVisibility(View.GONE);
//                    mBtnRegisterLink.setEnabled(true);
                    disableRegister();
                }else{

                    Gson gson = new Gson();
                    Type type = new TypeToken<PxRegNewUserResponse>() {}.getType();
                    PxRegNewUserResponse errorResponse = gson.fromJson(response.errorBody().charStream(),type);

                    AlertUtils.infoDialog(mContext, getString(R.string.warning),
                            errorResponse.getMessage());

                    mProgressBar.setVisibility(View.GONE);
                    disableRegister();
                }

            }

            @Override
            public void onFailure(Call<PxRegNewUserResponse> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
//                mBtnRegisterLink.setEnabled(true);
                disableRegister();
                LOGE(TAG, t.getMessage());
            }
        });


    }

    private boolean confirmPassword(){
        boolean result;
        final String pass = mEdPassword.getText().toString();
        final String retypepass = mEdRetypePassword.getText().toString();

        result = pass.equals(retypepass);
        return result;
    }


    private void initViews(View v) {
        isValidEntry = false;

//        final ViewGroup v = (ViewGroup) getActivity().findViewById(R.id.register_account_container);

        mEdUserName = (EditText) v.findViewById(R.id.editTextName);
        mEdEmail = (EditText) v.findViewById(R.id.editTextEmail);
        mEdPassword = (EditText) v.findViewById(R.id.user_new_password);
        mEdRetypePassword = (EditText) v.findViewById(R.id.user_retype_new_password);

        mChkTerm = (CheckBox) v.findViewById(R.id.chk_agree);
        mTvAgree = (TextView) v.findViewById(R.id.tv_agree);

        mChkTerm.setText("");
        mChkTerm.setChecked(false);
        UIUtils.setTextMaybeHtml(mTvAgree, mContext.getResources().getString(R.string.reg_terms));

        mTvAgree.setClickable(true);
        mTvAgree.setMovementMethod(LinkMovementMethod.getInstance());

        mBtnRegisterLink = (Button) v.findViewById(R.id.register_link);
        mBtnLoginLink = (Button) v.findViewById(R.id.login_link);
        mBtnRegisterLink.setEnabled(mChkTerm.isChecked());

        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

    }
}
