package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomCountResponse {

    @SerializedName("room")
    @Expose
    private Integer roomav;


    public Integer getRoomav() {
        return roomav;
    }

    public void setRoomav(Integer roomav) {
        this.roomav = roomav;
    }
}
