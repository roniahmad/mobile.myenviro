package com.rsbunda.myenviro.io.model.sales;

import com.google.gson.annotations.SerializedName;

public class ManPowerDetil {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("nip")
    private String nip;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("jabatan_id")
    private Integer jabatanId;

    @SerializedName("status")
    private Integer status;

    @SerializedName("avatar")
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public Integer getJabatanId() {
        return jabatanId;
    }

    public void setJabatanId(Integer jabatanId) {
        this.jabatanId = jabatanId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
