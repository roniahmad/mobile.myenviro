package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.SerializedName;

public class Organization {

    @SerializedName("uuid")
    private String uuid;
    @SerializedName("kode")
    private String kode;
    @SerializedName("perusahaan")
    private String perusahaan;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

}
