package com.rsbunda.myenviro.io.model.layanan;

import com.google.gson.annotations.SerializedName;

public class ProductLayanan {

    @SerializedName("id")
    private Integer id;

    @SerializedName("id_jenis_layanan")
    private Integer idJenisLayanan;

    @SerializedName("nama")
    private String nama;

    @SerializedName("narahubung")
    private String narahubung;

    @SerializedName("telp")
    private String telp;

    @SerializedName("email")
    private String email;

    @SerializedName("deskripsi")
    private String deskripsi;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdJenisLayanan() {
        return idJenisLayanan;
    }

    public void setIdJenisLayanan(Integer idJenisLayanan) {
        this.idJenisLayanan = idJenisLayanan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNarahubung() {
        return narahubung;
    }

    public void setNarahubung(String narahubung) {
        this.narahubung = narahubung;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
