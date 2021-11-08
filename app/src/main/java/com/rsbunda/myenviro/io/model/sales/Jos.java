package com.rsbunda.myenviro.io.model.sales;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Jos {

    @SerializedName("id")
    private Integer id;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_name")
    private String clientName;

    @SerializedName("client_code")
    private String clientCode;

    @SerializedName("jos_no")
    private String josNumber;

    @SerializedName("currency")
    private String currency;

    @SerializedName("scope_of_work")
    private String scopeOfWork;

    @SerializedName("start_date")
    private Date startDate;

    @SerializedName("end_date")
    private Date endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getJosNumber() {
        return josNumber;
    }

    public void setJosNumber(String josNumber) {
        this.josNumber = josNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getScopeOfWork() {
        return scopeOfWork;
    }

    public void setScopeOfWork(String scopeOfWork) {
        this.scopeOfWork = scopeOfWork;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
