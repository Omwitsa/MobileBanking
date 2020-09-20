package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class ClientModel {
    @SerializedName("fingerePrint")
    private String fingerePrint;
    @SerializedName("sNo")
    private String sNo;
    @SerializedName("pin")
    private String pin;

    public ClientModel(String fingerePrint, String sNo, String pin) {
        this.fingerePrint = fingerePrint;
        this.sNo = sNo;
        this.pin = pin;
    }

    public String getFingerePrint() {
        return fingerePrint;
    }

    public void setFingerePrint(String fingerePrint) {
        this.fingerePrint = fingerePrint;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
