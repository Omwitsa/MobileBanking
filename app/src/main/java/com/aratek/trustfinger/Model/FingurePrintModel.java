package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class FingurePrintModel {
    @SerializedName("fingerPrint")
    private String FingerPrint;
    @SerializedName("idNo")
    private String IdNo;

    public FingurePrintModel(String fingerPrint, String idNo) {
        FingerPrint = fingerPrint;
        IdNo = idNo;
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
}
