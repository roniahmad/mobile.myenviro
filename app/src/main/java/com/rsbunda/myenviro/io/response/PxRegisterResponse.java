package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.PxRegister;

public class PxRegisterResponse extends BaseResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("reg")
    @Expose
    private PxRegister reg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public PxRegister getReg() {
        return reg;
    }

    public void setReg(PxRegister reg) {
        this.reg = reg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + String.valueOf(success) +
                ", message='" + message + '\'' +
                ", reg='" + getReg() + '\'' +
                '}';
    }
}
