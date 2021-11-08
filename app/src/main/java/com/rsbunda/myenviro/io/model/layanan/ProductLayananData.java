package com.rsbunda.myenviro.io.model.layanan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Pagination;

import java.util.ArrayList;
import java.util.List;

public class ProductLayananData {

    @SerializedName("data")
    @Expose
    private List<ProductLayanan> layanan = new ArrayList<>();

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<ProductLayanan> getLayanan() {
        return layanan;
    }

    public void setLayanan(List<ProductLayanan> layanan) {
        this.layanan = layanan;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
