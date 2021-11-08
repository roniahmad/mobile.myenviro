package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PxRefreshToken extends BaseResponse {

    @SerializedName("token")
    @Expose
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
