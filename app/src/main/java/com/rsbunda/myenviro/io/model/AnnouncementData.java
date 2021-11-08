package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementData {

    @SerializedName("data")
    @Expose
    private List<Announcement> announcement = new ArrayList<>();
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Announcement> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<Announcement> announcement) {
        this.announcement = announcement;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
