package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class SecondNameModel {
    @SerializedName("idno")
    private String idno;



    public SecondNameModel(String idno) {

        this.idno = idno;

    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

}
