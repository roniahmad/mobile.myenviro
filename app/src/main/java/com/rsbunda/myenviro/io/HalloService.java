package com.rsbunda.myenviro.io;

import com.rsbunda.myenviro.io.model.FeedbackResponse;
import com.rsbunda.myenviro.io.model.OrganizationData;
import com.rsbunda.myenviro.io.model.activity.DailyData;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportData;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportImagesData;
import com.rsbunda.myenviro.io.model.client.RefreshToken;
import com.rsbunda.myenviro.io.model.layanan.LayananData;
import com.rsbunda.myenviro.io.model.layanan.ProductLayananData;
import com.rsbunda.myenviro.io.model.sales.JosData;
import com.rsbunda.myenviro.io.model.sales.ManPowerDetilData;
import com.rsbunda.myenviro.io.response.ChangePasswordResponse;
import com.rsbunda.myenviro.io.response.FcmResponse;
import com.rsbunda.myenviro.io.response.HalloResponse;
import com.rsbunda.myenviro.io.response.BaseResponse;
import com.rsbunda.myenviro.io.response.LoginResponse;
import com.rsbunda.myenviro.io.response.PxRegNewUserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HalloService {

    @FormUrlEncoded
    @POST("clientauth/v1/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password);

    @POST("clientauth/v1/logout")
    Call<HalloResponse> logout(
            @Header("Authorization") String token);

    @GET("layanan/v1/layanan")
    Call<LayananData> layanan(@Header("Authorization") String token);

    @GET("layanan/v1/product_layanan")
    Call<ProductLayananData> productlayanan(@Header("Authorization") String token);

    //----------Start Cleaning Block

    //Get Daily Activity Report by JOS Id
    @GET("cleaning/v1/darbyjos")
    Call<DailyReportData> darbyjos(
            @Header("Authorization") String token,
            @Query("jos_id") int josid,
            @Query("client_id") String clientId,
            @Query("date_report") String dateReport);

    //Get Daily Activity Report by JOS Id
    @GET("cleaning/client/v1/dailyreportimage")
    Call<DailyReportImagesData> dailyreportimage(
            @Header("Authorization") String token,
            @Query("jos_id") int josid,
            @Query("date_report") String dateReport);

    @GET("sales/v1/josbyclient")
    Call<JosData> josbyclient(
            @Header("Authorization") String token,
            @Query("klien_id") String client);

    @POST("clientauth/v1/refresh")
    Call<RefreshToken> clientrefreshtoken(
            @Header("Authorization") String token);

    //Relogin token
    @FormUrlEncoded
    @POST("account/v1/relogin")
    Call<RefreshToken> relogin(
            @Field("email") String email,
            @Field("password") String password);


    @GET("sales/v1/clientjmpdbyjosid")
    Call<ManPowerDetilData> manpowerbyjos(
            @Header("Authorization") String token,
            @Query("jos_id") int josid);

    @GET("cleaning/client/v1/dacbyjosjob")
    Call<DailyData> dacbyjosjob(
            @Header("Authorization") String token,
            @Query("jos_id") int josid,
            @Query("job_id") int jobid);


    @FormUrlEncoded
    @POST("pxchgpwd")
    Call<ChangePasswordResponse> changepassword(
            @Header("Authorization") String token,
            @Field("old_password") String oldpassword,
            @Field("new_password") String newpassword,
            @Field("confirm_password") String confirmpassword);

    @FormUrlEncoded
    @POST("register")
    Call<HalloResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("pxregnewuser")
    Call<PxRegNewUserResponse> pxregnewuser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("pxregrating")
    Call<FeedbackResponse> pxregrating(
            @Header("Authorization") String token,
            @Field("id") int id,
            @Field("rating") int rating,
            @Field("feedback") String feedback);

    @FormUrlEncoded
    @POST("pxregtoken")
    Call<FcmResponse> pxregtoken(
            @Header("Authorization") String token,
            @Field("email") String email,
            @Field("firebase_token") String firebase_token);

    @FormUrlEncoded
    @POST("pxunregtoken")
    Call<FcmResponse> pxunregtoken(
            @Header("Authorization") String token,
            @Field("email") String email);

    @FormUrlEncoded
    @POST("pxresetpwd")
    Call<BaseResponse> resetpwd(
            @Header("Authorization") String token,
            @Field("email") String email);



    @GET("getoulist")
    Call<OrganizationData> getoulist();

}
