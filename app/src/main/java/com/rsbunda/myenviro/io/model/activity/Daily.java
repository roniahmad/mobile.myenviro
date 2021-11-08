package com.rsbunda.myenviro.io.model.activity;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;

public class Daily {

    @SerializedName("id")
    private Integer id;

    @SerializedName("mulai")
    private Time start;

    @SerializedName("selesai")
    private Time end;

    @SerializedName("pekerjaan")
    private String job;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
