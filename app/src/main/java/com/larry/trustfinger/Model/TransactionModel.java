package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class TransactionModel {
    @SerializedName("operation")
    private String operation;
    @SerializedName("amount")
    private Double amount;
    @SerializedName("fingerePrint")
    private String fingerePrint;
    @SerializedName("pin")
    private String pin;
    @SerializedName("sNo")
    private String sNo;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Did")
    private String Did;
    @SerializedName("status")
    private String status;
    @SerializedName("machineID")
    private String machineID;
    @SerializedName("auditID")
    private String auditID;
    @SerializedName("productDescription")
    private String productDescription;
    @SerializedName("AgencyName")
    private String AgencyName;
    @SerializedName("accountNo")
    private String accountNo;

    public TransactionModel(String operation, Double amount, String Name, String Did, String fingerePrint, String pin, String sNo, String status, String machineID, String auditID, String productDescription,String accountNo) {
        this.operation = operation;

        this.amount = amount;
        this.fingerePrint = fingerePrint;
        this.pin = pin;
        this.sNo = sNo;
        this.Did = Did;
        this.Name = Name;
        this.status = status;
        this.machineID = machineID;
        this.auditID = auditID;
        this.productDescription = productDescription;
        this.AgencyName = AgencyName;
        this.accountNo = accountNo;
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


    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String Did) {
        this.Did = Did;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public String getAuditID() {
        return auditID;
    }

    public void setAuditID(String auditID) {
        this.auditID = auditID;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getAgencyName() {
        return AgencyName;
    }

    public void setAgencyName(String AgencyName) {
        this.AgencyName = AgencyName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
