package com.rsbunda.myenviro.io.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsbunda.myenviro.io.model.Hero;
import com.rsbunda.myenviro.io.model.Organization;
import com.rsbunda.myenviro.io.model.Token;

public class LoginResponse extends BaseResponse {

    @SerializedName("token")
    @Expose
    private Token token;

    @SerializedName("hero")
    @Expose
    private Hero hero;

    @SerializedName("org")
    @Expose
    private Organization org;


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero error) {
        this.hero = error;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + String.valueOf(success) +
                ", message='" + message + '\'' +
                ", token='" + token.getToken() + '\'' +
                '}';
    }
}
