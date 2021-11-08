package com.rsbunda.myenviro.io.model.layanan;

import com.google.gson.annotations.SerializedName;

public class Layanan {

    @SerializedName("id")
    private Integer id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("id_jenis_layanan")
    private Integer idJenisLayanan;

    @SerializedName("jenis_layanan")
    private String jenisLayanan;

    @SerializedName("gambar")
    private String gambar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getIdJenisLayanan() {
        return idJenisLayanan;
    }

    public void setIdJenisLayanan(Integer idJenisLayanan) {
        this.idJenisLayanan = idJenisLayanan;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
