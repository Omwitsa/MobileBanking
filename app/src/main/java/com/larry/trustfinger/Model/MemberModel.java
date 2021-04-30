package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class MemberModel {
    @SerializedName("username")
    private String usercode;
    @SerializedName("password")
    private String password;
    @SerializedName("machineId")
    private String machineId;

    public MemberModel(String usercode, String password, String machineId) {
        this.usercode = usercode;
        this.password = password;
        this.machineId = machineId;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}
