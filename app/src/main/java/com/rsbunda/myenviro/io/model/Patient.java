package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Patient {

    @SerializedName("idrm")
    private String idrm;
    @SerializedName("nama")
    private String nama;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("identitas")
    private String identitas;
    @SerializedName("no_bpjs")
    private String nobpjs;
    @SerializedName("poli_name")
    private String poliName;
    @SerializedName("date_visit")
    private Date dateVisit;

    public String getIdrm() {
        return idrm;
    }

    public void setIdrm(String idrm) {
        this.idrm = idrm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIdentitas() {
        return identitas;
    }

    public void setIdentitas(String identitas) {
        this.identitas = identitas;
    }

    public String getNobpjs() {
        return nobpjs;
    }

    public void setNobpjs(String nobpjs) {
        this.nobpjs = nobpjs;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPoliName() {
        return poliName;
    }

    public void setPoliName(String poliName) {
        this.poliName = poliName;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }
}
