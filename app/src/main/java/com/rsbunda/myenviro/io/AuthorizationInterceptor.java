package com.rsbunda.myenviro.io;

import android.content.Context;

import com.rsbunda.myenviro.io.model.client.RefreshToken;
import com.rsbunda.myenviro.login.LoginActivity;
import com.rsbunda.myenviro.util.AESUtils;
import com.rsbunda.myenviro.util.LoginUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class AuthorizationInterceptor implements Interceptor {

    private static final String TAG = makeLogTag(AuthorizationInterceptor.class);
    private HalloService halloService;
    private Context mContext;

    public AuthorizationInterceptor (Context ctx){
        this.mContext = ctx;
        this.halloService = HalloGenerator.createService(HalloService.class, ctx);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(chain.request());

        if(response.code() == 401 || response.code() == 403){
            final String email = LoginUtils.getUserName(this.mContext);
            final String password = LoginUtils.getUserPassword(this.mContext);
            LOGD(TAG, "EMAIL :" + email);
            LOGD(TAG, "PASSWORD :" + password);

            String decrypted = "";
            try {
                decrypted = AESUtils.decrypt(password);
                LOGD(TAG, "decrypted:" + decrypted);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.close();
            final String token = "Bearer "+LoginUtils.getUserToken(this.mContext);

            retrofit2.Response<RefreshToken> refreshToken = this.halloService.relogin(email, decrypted).execute();

            if(refreshToken.isSuccessful()){
                final String rtoken = refreshToken.body().getToken();

                LoginUtils.setUserToken(this.mContext, rtoken);
                LoginUtils.markUserHasLogin(this.mContext, true);

                // retry the 'request' which encountered an authentication error
                // add new token into 'request' header and request again
                Request.Builder builder = request.newBuilder().header("Authorization", "Bearer " + rtoken);
                response = chain.proceed(builder.build());
            }else{
                LoginUtils.markUserHasLogin(mContext, false);
                LoginUtils.setUserToken(mContext, null);
                LoginUtils.setUserName(mContext, null);
                LoginActivity.startLoginActivity(mContext);
            }
        }

        return response;
    }
}
