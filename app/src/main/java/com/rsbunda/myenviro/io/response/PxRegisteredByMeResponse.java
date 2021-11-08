package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PxRegisteredByMeResponse {

    @SerializedName("reg")
    @Expose
    private Integer reg;

    public Integer getReg() {
        return reg;
    }

    public void setReg(Integer reg) {
        this.reg = reg;
    }
}
