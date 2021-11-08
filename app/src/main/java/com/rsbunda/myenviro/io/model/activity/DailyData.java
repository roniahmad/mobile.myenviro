package com.rsbunda.myenviro.io.model.activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Pagination;

import java.util.ArrayList;
import java.util.List;

public class DailyData {

    @SerializedName("data")
    @Expose
    private List<Daily> daily = new ArrayList<>();

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
