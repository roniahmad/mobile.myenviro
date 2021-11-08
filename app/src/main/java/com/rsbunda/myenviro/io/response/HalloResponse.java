package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Organization;


public class HalloResponse extends BaseResponse {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("org")
    @Expose
    private Organization org;

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return The error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + String.valueOf(success) +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
