package com.rsbunda.myenviro.io.model.cleaning;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class DailyReport {

    @SerializedName("id")
    private Integer id;

    @SerializedName("jos_id")
    private Integer josId;

    @SerializedName("pegawai_id")
    private Integer pegawaiId;

    @SerializedName("tanggal_lapor")
    private Date tanggalLapor;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("rekomendasi")
    private String rekomendasi;

    @SerializedName("feedback_klien")
    private String feedbackKlien;

    @SerializedName("tanggal_rekomendasi")
    private Date tanggalRekomendasi;

    @SerializedName("waktu_rekomendasi")
    private Time waktuRekomendasi;

    @SerializedName("tanggal_feedback")
    private Date tanggalFeedback;

    @SerializedName("waktu_feedback")
    private Time waktuFeedback;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTanggalLapor() {
        return tanggalLapor;
    }

    public void setTanggalLapor(Date tanggalLapor) {
        this.tanggalLapor = tanggalLapor;
    }

    public Integer getPegawaiId() {
        return pegawaiId;
    }

    public void setPegawaiId(Integer pegawaiId) {
        this.pegawaiId = pegawaiId;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getJosId() {
        return josId;
    }

    public void setJosId(Integer josId) {
        this.josId = josId;
    }

    public String getRekomendasi() {
        return rekomendasi;
    }

    public void setRekomendasi(String rekomendasi) {
        this.rekomendasi = rekomendasi;
    }

    public Time getWaktuRekomendasi() {
        return waktuRekomendasi;
    }

    public void setWaktuRekomendasi(Time waktuRekomendasi) {
        this.waktuRekomendasi = waktuRekomendasi;
    }

    public Time getWaktuFeedback() {
        return waktuFeedback;
    }

    public void setWaktuFeedback(Time waktuFeedback) {
        this.waktuFeedback = waktuFeedback;
    }

    public String getFeedbackKlien() {
        return feedbackKlien;
    }

    public void setFeedbackKlien(String feedbackKlien) { this.feedbackKlien = feedbackKlien; }

    public Date getTanggalRekomendasi() {
        return tanggalRekomendasi;
    }

    public void setTanggalRekomendasi(Date tanggalRekomendasi) {
        this.tanggalRekomendasi = tanggalRekomendasi;
    }

    public Date getTanggalFeedback() {
        return tanggalFeedback;
    }

    public void setTanggalFeedback(Date tanggalFeedback) {
        this.tanggalFeedback = tanggalFeedback;
    }
}
