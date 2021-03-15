package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class TellerDate {

    @SerializedName("idNo")
    private String idNo;
    @SerializedName("machineId")
    private String machineId;


    public TellerDate(String idNo,String machineId) {

        this.idNo = idNo;
        this.machineId = machineId;
    }


    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMachineId() {
        return machineId;
    }




}
