package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnouncementDetail {
    @SerializedName("data")
    @Expose
    private Announcement announcement;

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
