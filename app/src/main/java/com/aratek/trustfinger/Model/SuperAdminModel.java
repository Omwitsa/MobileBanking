package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class SuperAdminModel {
    @SerializedName("idno")
    private String idno;



    public SuperAdminModel(String idno) {

        this.idno = idno;

    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

}