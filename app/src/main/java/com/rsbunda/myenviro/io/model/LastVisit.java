package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class LastVisit {

    @SerializedName("id")
    private Integer id;
    @SerializedName("ou_fk")
    private  String ouFK;
    @SerializedName("idrm")
    private  String idrm;
    @SerializedName("queue_number")
    private Integer queue_number;
    @SerializedName("poli_name")
    private  String poliName;
    @SerializedName("code_alpha")
    private  String codeAlpha;
    @SerializedName("date_visit")
    private Date dateVisit;
    @SerializedName("time_start" )
    private Time timeStart;
    @SerializedName("doctor_fk")
    private Integer doctorFk;
    @SerializedName("doctor_name")
    private  String doctorName;
    @SerializedName("avatar")
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOuFK() {
        return ouFK;
    }

    public void setOuFK(String ouFK) {
        this.ouFK = ouFK;
    }

    public String getIdrm() {
        return idrm;
    }

    public void setIdrm(String idrm) {
        this.idrm = idrm;
    }

    public Integer getQueue_number() {
        return queue_number;
    }

    public void setQueue_number(Integer queue_number) {
        this.queue_number = queue_number;
    }

    public String getPoliName() {
        return poliName;
    }

    public void setPoliName(String poliName) {
        this.poliName = poliName;
    }

    public String getCodeAlpha() {
        return codeAlpha;
    }

    public void setCodeAlpha(String codeAlpha) {
        this.codeAlpha = codeAlpha;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getDoctorFk() {
        return doctorFk;
    }

    public void setDoctorFk(Integer doctorFk) {
        this.doctorFk = doctorFk;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
