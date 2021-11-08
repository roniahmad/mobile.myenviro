package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Organization;
import com.rsbunda.myenviro.io.model.Patient;

public class PxRegNewUserResponse extends BaseResponse {


    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("org")
    @Expose
    private Organization org;
    @SerializedName("px")
    @Expose
    private Patient patient;
    @SerializedName("mindays")
    @Expose
    private Integer mindays;
    @SerializedName("isvalidated")
    @Expose
    private Integer isvalidated;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getMindays() {
        return mindays;
    }

    public void setMindays(Integer mindays) {
        this.mindays = mindays;
    }

    public Integer getIsvalidated() {
        return isvalidated;
    }

    public void setIsvalidated(Integer isvalidated) {
        this.isvalidated = isvalidated;
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
