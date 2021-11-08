package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PxWaitingTimeResponse {

    @SerializedName("wt")
    @Expose
    private Integer waitingTime;

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }
}
