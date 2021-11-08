package com.rsbunda.myenviro.io.model.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.response.BaseResponse;

public class RefreshToken extends BaseResponse {

    @SerializedName("access_token")
    @Expose
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
