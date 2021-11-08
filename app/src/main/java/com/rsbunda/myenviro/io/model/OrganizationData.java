package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrganizationData {
    @SerializedName("data")
    @Expose
    private List<Organization> organization = new ArrayList<>();
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Organization> getOrganization() {
        return organization;
    }

    public void setOrganization(List<Organization> organization) {
        this.organization = organization;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
