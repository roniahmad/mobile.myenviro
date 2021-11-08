package com.rsbunda.myenviro.io.model.cleaning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Pagination;

import java.util.ArrayList;
import java.util.List;

public class DailyReportImagesData {

    @SerializedName("data")
    @Expose
    private List<DailyReportImages> dailyReportImages = new ArrayList<>();

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<DailyReportImages> getDailyReportImages() {
        return dailyReportImages;
    }

    public void setDailyReportImages(List<DailyReportImages> dailyReportImages) {
        this.dailyReportImages = dailyReportImages;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
