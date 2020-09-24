package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class AgentMember {
    @SerializedName("surname")
    private String surname;
    @SerializedName("otherName")
    private String otherName;
    @SerializedName("idNo")
    private String idNo;
    @SerializedName("mobileNo")
    private String mobileNo;
    @SerializedName("gender")
    private String gender;
    @SerializedName("dob")
    private String dob;
    @SerializedName("finerPrint1")
    private String finerPrint1;
    @SerializedName("fingerPrint2")
    private String fingerPrint2;
    @SerializedName("agentId")
    private String agentId;

    public AgentMember(String surname, String otherName, String idNo, String mobileNo, String gender, String dob, String finerPrint1, String fingerPrint2, String agentId) {
        this.surname = surname;
        this.otherName = otherName;
        this.idNo = idNo;
        this.mobileNo = mobileNo;
        this.gender = gender;
        this.dob = dob;
        this.finerPrint1 = finerPrint1;
        this.fingerPrint2 = fingerPrint2;
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

    public String getFinerPrint1() {
        return finerPrint1;
    }

    public void setFinerPrint1(String finerPrint1) {
        this.finerPrint1 = finerPrint1;
    }

    public String getFingerPrint2() {
        return fingerPrint2;
    }

    public void setFingerPrint2(String fingerPrint2) {
        this.fingerPrint2 = fingerPrint2;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
