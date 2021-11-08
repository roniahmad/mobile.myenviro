package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class Announcement {

    @SerializedName("id")
    private Integer id;
    @SerializedName("ou_fk" )
    private String ou_fk;
    @SerializedName("ou_name" )
    private String ou_name;
    @SerializedName("title" )
    private String title;
    @SerializedName("description")
    private  String description;
    @SerializedName("desclen")
    private Integer desclen;
    @SerializedName("due_date" )
    private Date dueDate;
    @SerializedName("due_time")
    private Time dueTime;
    @SerializedName("is_private")
    private Integer is_private;
    @SerializedName("venue_name" )
    private String venueName;
    @SerializedName("location_address")
    private String location_address;
    @SerializedName("location_state")
    private String locationState;
    @SerializedName("location_post_code")
    private String locationPostCode;
    @SerializedName("contact_person")
    private String contactPerson;
    @SerializedName("contact_phone")
    private String contactPhone;
    @SerializedName("contact_email")
    private String contactEmail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDesclen() {
        return desclen;
    }

    public void setDesclen(Integer desclen) {
        this.desclen = desclen;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getLocationPostCode() {
        return locationPostCode;
    }

    public void setLocationPostCode(String locationPostCode) {
        this.locationPostCode = locationPostCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getOuFk() {
        return ou_fk;
    }

    public void setOuFk(String ou_fk) {
        this.ou_fk = ou_fk;
    }

    public String getOuName() {
        return ou_name;
    }

    public void setOuName(String ou_name) {
        this.ou_name = ou_name;
    }

    public Integer getIsPrivate() {
        return is_private;
    }

    public void setIsPrivate(Integer is_private) {
        this.is_private = is_private;
    }
}
