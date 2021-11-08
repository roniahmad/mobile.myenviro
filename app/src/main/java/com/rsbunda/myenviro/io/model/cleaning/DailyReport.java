package com.rsbunda.myenviro.io.model.cleaning;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class DailyReport {

    @SerializedName("id")
    private Integer id;

    @SerializedName("tanggal_lapor")
    private Date tanggalLapor;

    @SerializedName("laporan_dac_id")
    private Integer laporanDacId;

    @SerializedName("jp_id")
    private Integer jenisPekerjaanId;

    @SerializedName("jenis_pekerjaan")
    private String jenisPekerjaan;

    @SerializedName("joi")
    private Integer josAreaId;

    @SerializedName("area")
    private String area;

    @SerializedName("mulai")
    private Time mulai;

    @SerializedName("selesai")
    private Time selesai;

    @SerializedName("pekerjaan")
    private String reportPekerjaan;

    @SerializedName("catatan")
    private String reportCatatan;


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

    public Integer getLaporanDacId() {
        return laporanDacId;
    }

    public void setLaporanDacId(Integer laporanDacId) {
        this.laporanDacId = laporanDacId;
    }

    public Integer getJenisPekerjaanId() {
        return jenisPekerjaanId;
    }

    public void setJenisPekerjaanId(Integer jenisPekerjaanId) {
        this.jenisPekerjaanId = jenisPekerjaanId;
    }

    public String getJenisPekerjaan() {
        return jenisPekerjaan;
    }

    public void setJenisPekerjaan(String jenisPekerjaan) {
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public Integer getJosAreaId() {
        return josAreaId;
    }

    public void setJosAreaId(Integer josAreaId) {
        this.josAreaId = josAreaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Time getMulai() {
        return mulai;
    }

    public void setMulai(Time mulai) {
        this.mulai = mulai;
    }

    public Time getSelesai() {
        return selesai;
    }

    public void setSelesai(Time selesai) {
        this.selesai = selesai;
    }

    public String getReportPekerjaan() {
        return reportPekerjaan;
    }

    public void setReportPekerjaan(String reportPekerjaan) {
        this.reportPekerjaan = reportPekerjaan;
    }

    public String getReportCatatan() {
        return reportCatatan;
    }

    public void setReportCatatan(String reportCatatan) {
        this.reportCatatan = reportCatatan;
    }
}
