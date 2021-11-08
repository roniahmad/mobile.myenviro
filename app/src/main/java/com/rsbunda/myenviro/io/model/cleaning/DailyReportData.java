package com.rsbunda.myenviro.io.model.cleaning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Pagination;

import java.util.ArrayList;
import java.util.List;

public class DailyReportData {

    @SerializedName("data")
    @Expose
    private List<DailyReport> dailyReport = new ArrayList<>();

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<DailyReport> getDailyReport() {
        return dailyReport;
    }

    public void setDailyReport(List<DailyReport> dailyReport) {
        this.dailyReport = dailyReport;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
