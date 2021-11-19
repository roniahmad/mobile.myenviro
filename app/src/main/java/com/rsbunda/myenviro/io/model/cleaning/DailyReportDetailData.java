package com.rsbunda.myenviro.io.model.cleaning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Pagination;

import java.util.ArrayList;
import java.util.List;

public class DailyReportDetailData {

    @SerializedName("data")
    @Expose
    private List<DailyReportDetail> dailyReportDetail = new ArrayList<>();

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<DailyReportDetail> getDailyReport() {
        return dailyReportDetail;
    }

    public void setDailyReport(List<DailyReportDetail> dailyReportDetail) {
        this.dailyReportDetail = dailyReportDetail;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
