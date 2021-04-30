package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterFingerprints {


    @SerializedName("idNo")
    private String IdNo;
    @SerializedName("fingerPrint")
    private String FingerPrint;
    @SerializedName("machineId")
    private String MachineId;



    public RegisterFingerprints(String fingerPrint,String idNo,String machineId)
    {
        FingerPrint = fingerPrint;
        IdNo = idNo;
        MachineId = machineId;
    }
    public String getFingerPrint() {
        return FingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        FingerPrint = fingerPrint;
    }
    public String getIdNo() { return IdNo; }

    public void setIdNo(String idNo) { IdNo = idNo;}

    public String getMachineId() { return MachineId; }

    public void setMachineId(String machineId) { MachineId = machineId;}
}
