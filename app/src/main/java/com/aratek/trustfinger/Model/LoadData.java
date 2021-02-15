package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class LoadData {
    @SerializedName("machineId")
    private String machineId;
    public LoadData(String machineId) {
        this.machineId = machineId;
    }
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}
