package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistoryData {

    @SerializedName("data")
    @Expose
    private List<History> history = new ArrayList<>();
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
