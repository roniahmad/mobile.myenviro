package com.rsbunda.myenviro.io.model.sales;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Pagination;

import java.util.ArrayList;
import java.util.List;

public class JosData {
    @SerializedName("data")
    @Expose
    private List<Jos> jos = new ArrayList<>();

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Jos> getJos() {
        return jos;
    }

    public void setJos(List<Jos> jos) {
        this.jos = jos;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
