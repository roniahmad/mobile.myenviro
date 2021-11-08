package com.rsbunda.myenviro.io.model.sales;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Pagination;

import java.util.ArrayList;
import java.util.List;

public class ManPowerDetilData {

    @SerializedName("data")
    @Expose
    private List<ManPowerDetil> manPowerDetils = new ArrayList<>();

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<ManPowerDetil> getManPowerDetils() {
        return manPowerDetils;
    }

    public void setManPowerDetils(List<ManPowerDetil> manPowerDetils) {
        this.manPowerDetils = manPowerDetils;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
