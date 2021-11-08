package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PxBpjsPesertaActiveResponse extends ApiBaseResponse {

//    @SerializedName("code")
//    @Expose
//    private String code;
//
//    @SerializedName("message")
//    @Expose
//    private String message;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("nik")
    @Expose
    private String nik;

    @SerializedName("nokartu")
    @Expose
    private String nokartu;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("keterangan")
    @Expose
    private String keterangan;



    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNokartu() {
        return nokartu;
    }

    public void setNokartu(String nokartu) {
        this.nokartu = nokartu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
