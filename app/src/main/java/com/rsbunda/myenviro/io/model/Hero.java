package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.SerializedName;

public class Hero {

    @SerializedName("verified")
    private Integer verified;

    @SerializedName("username")
    private String username;

    @SerializedName("tipe_user")
    private Integer tipeUser;

    @SerializedName("pegawai_id")
    private Integer pegawaiId;

    @SerializedName("nip")
    private String nip;

    @SerializedName("nama")
    private String nama;

    @SerializedName("gelar_depan")
    private String gelar_depan;

    @SerializedName("gelar_belakang")
    private String gelarBelakang;


    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTipeUser() {
        return tipeUser;
    }

    public void setTipeUser(Integer tipeUser) {
        this.tipeUser = tipeUser;
    }

    public Integer getPegawaiId() {
        return pegawaiId;
    }

    public void setPegawaiId(Integer pegawaiId) {
        this.pegawaiId = pegawaiId;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGelarDepan() {
        return gelar_depan;
    }

    public void setGelarDepan(String gelar_depan) {
        this.gelar_depan = gelar_depan;
    }

    public String getGelarBelakang() {
        return gelarBelakang;
    }

    public void setGelarBelakang(String gelarBelakang) {
        this.gelarBelakang = gelarBelakang;
    }
}
