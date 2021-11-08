package com.rsbunda.myenviro.io.model.cleaning;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class DailyReportImages {

    @SerializedName("id")
    private Integer id;

    @SerializedName("ldd_id")
    private Integer lddId;

    @SerializedName("filename")
    private String filename;

    @SerializedName("tgl_capture")
    private Date tglCapture;

    @SerializedName("jam_capture")
    private Time jamCapture;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLddId() {
        return lddId;
    }

    public void setLddId(Integer lddId) {
        this.lddId = lddId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getTglCapture() {
        return tglCapture;
    }

    public void setTglCapture(Date tglCapture) {
        this.tglCapture = tglCapture;
    }

    public Time getJamCapture() {
        return jamCapture;
    }

    public void setJamCapture(Time jamCapture) {
        this.jamCapture = jamCapture;
    }
}
