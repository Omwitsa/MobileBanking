package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class FingurePrintModel {
    @SerializedName("fingerPrint")
    private String FingerPrint;
    @SerializedName("idNo")
    private String IdNo;
    @SerializedName("machineId")
    private String MachineId;

    public FingurePrintModel(String fingerPrint, String idNo,String machineId) {
        FingerPrint = fingerPrint;
        IdNo = idNo;
        MachineId=machineId;
    }

    public String getFingerPrint() {
        return FingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        FingerPrint = fingerPrint;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }

    public String getmachineId() {
        return MachineId;
    }

    public void setmachineId(String machineId) {
        MachineId = machineId;
    }
}
