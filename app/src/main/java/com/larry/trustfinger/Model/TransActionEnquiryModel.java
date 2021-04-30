package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class TransActionEnquiryModel {
    @SerializedName("idno")
    private String idno;
    @SerializedName("MachineID")
    private String MachineID;



    public TransActionEnquiryModel(String idno,String MachineID) {

        this.idno = idno;
        this.MachineID = MachineID;

    }

    public String getIdno() { return idno; }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMachineID() { return MachineID; }

    public void setMachineID(String MachineID) {
        this.MachineID = MachineID;
    }
}
