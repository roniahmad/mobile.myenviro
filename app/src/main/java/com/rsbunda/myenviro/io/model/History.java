package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class History {

    @SerializedName("id")
    private Integer id;

    @SerializedName("ou_fk")
    private String oufk;

    @SerializedName("date_visit")
    private Date dateVisit;

    @SerializedName("is_cancelled")
    private Integer isCancelled;

    @SerializedName("is_next")
    private Integer isNext;

    @SerializedName("is_skip")
    private Integer isSkip;

    @SerializedName("has_given_rating")
    private Integer hasGivenRating;

    @SerializedName("code_alpha")
    private  String codeAlpha;

    @SerializedName("ticket_no")
    private  String ticketNo;

    @SerializedName("poli_name")
    private  String poliName;

    @SerializedName("doctor_fk")
    private Integer doctorFk;

    @SerializedName("doctor_name")
    private  String doctorName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOufk() {
        return oufk;
    }

    public void setOufk(String oufk) {
        this.oufk = oufk;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public Integer getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }

    public boolean userHasCancelled(){
        return this.isCancelled==1;
    }

    public boolean userCanGiveRating(){
        final boolean canGiveRating =  (this.isCancelled==0) && (this.isNext==1)
                && (this.hasGivenRating==0);
        return canGiveRating;
    }

    public Integer getIsNext() {
        return isNext;
    }

    public void setIsNext(Integer isNext) {
        this.isNext = isNext;
    }

    public Integer getIsSkip() {
        return isSkip;
    }

    public void setIsSkip(Integer isSkip) {
        this.isSkip = isSkip;
    }

    public Integer getHasGivenRating() {
        return hasGivenRating;
    }

    public void setHasGivenRating(Integer hasGivenRating) {
        this.hasGivenRating = hasGivenRating;
    }

    public boolean hasGivenRating() {
        return this.hasGivenRating==1;
    }

    public String getCodeAlpha() {
        return codeAlpha;
    }

    public void setCodeAlpha(String codeAlpha) {
        this.codeAlpha = codeAlpha;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getPoliName() {
        return poliName;
    }

    public void setPoliName(String poliName) {
        this.poliName = poliName;
    }

    public Integer getDoctorFk() {
        return doctorFk;
    }

    public void setDoctorFk(Integer doctorFk) {
        this.doctorFk = doctorFk;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }


}
