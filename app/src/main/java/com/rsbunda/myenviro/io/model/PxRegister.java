package com.rsbunda.myenviro.io.model;

import com.google.gson.annotations.SerializedName;

public class PxRegister {

    @SerializedName("id")
    private Integer id;

    @SerializedName("idrm" )
    private String idrm;

    @SerializedName("schedule_poly_id" )
    private String scheduleId;

    @SerializedName("date_visit" )
    private String dateVisit;

    @SerializedName("queue_number")
    private Integer queueNumber;

    @SerializedName("ticket_no" )
    private String ticketNo;

}
