package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class SecondNameModel {
    @SerializedName("idno")
    private String idno;
    @SerializedName("machineID")
    private String machineID;



    public SecondNameModel(String idno, String machineID) {

        this.idno = idno;
        this.machineID = machineID;

    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

}
