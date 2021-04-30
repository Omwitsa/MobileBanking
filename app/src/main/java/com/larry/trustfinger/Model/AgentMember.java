package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class AgentMember {
    @SerializedName("Surname")
    private String surname;
    @SerializedName("other_Names")
    private String otherName;
    @SerializedName("idno")
    private String idNo;
    @SerializedName("MachineId")
    private String machineId;
    @SerializedName("mobile_number")
    private String mobileNo;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("DOB")
    private String dob;
    @SerializedName("FingerPrint1")
    private String fingerprint1;
    @SerializedName("FingerPrint2")
    private String fingerprint2;
    @SerializedName("Agentid")
    private String agentId;

    public AgentMember(String surname, String otherName, String idNo,String machineId, String mobileNo, String gender, String dob, String fingerprint1, String fingerprint2, String agentId) {
        this.surname = surname;
        this.otherName = otherName;
        this.idNo = idNo;
        this.machineId = machineId;
        this.mobileNo = mobileNo;
        this.gender = gender;
        this.dob = dob;
        this.fingerprint1= fingerprint1;
        this.fingerprint2 = fingerprint2;
        this.agentId = agentId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
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

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFingerprint1() {
        return fingerprint1;
    }

    public void setFingerprint1(String fingerprint1) {
        this.fingerprint1 = fingerprint1;
    }

    public String getFingerprint2() {
        return fingerprint2;
    }

    public void setFingerprint2(String fingerprint2) {
        this.fingerprint2 = fingerprint2;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
