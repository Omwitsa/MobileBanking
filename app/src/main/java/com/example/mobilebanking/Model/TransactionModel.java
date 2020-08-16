package com.example.mobilebanking.Model;

import com.google.gson.annotations.SerializedName;

public class TransactionModel {
    @SerializedName("operation")
    private  String operation;
    @SerializedName("amount")
    private Double amount;
    @SerializedName("fingerePrint")
    private  String fingerePrint;
    @SerializedName("pin")
    private String pin;
    @SerializedName("sNo")
    private  String sNo;
    @SerializedName("status")
    private  String status;
    @SerializedName("Machineid")
    private  String Machineid;

    public TransactionModel(String operation, Double amount, String fingerePrint, String pin, String sNo, String status, String machineid) {
        this.operation = operation;
        this.amount = amount;
        this.fingerePrint = fingerePrint;
        this.pin = pin;
        this.sNo = sNo;
        this.status = status;
        Machineid = machineid;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFingerePrint() {
        return fingerePrint;
    }

    public void setFingerePrint(String fingerePrint) {
        this.fingerePrint = fingerePrint;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMachineid() {
        return Machineid;
    }

    public void setMachineid(String machineid) {
        Machineid = machineid;
    }
}
