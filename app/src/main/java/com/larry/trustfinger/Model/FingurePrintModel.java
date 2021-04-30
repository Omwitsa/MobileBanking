package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class FingurePrintModel {
    @SerializedName("fingerPrint")
    private String FingerPrint;
    @SerializedName("idNo")
    private String IdNo;
    @SerializedName("machineId")
    private String MachineId;
    @SerializedName("agentId")
    private String AgentId;
    @SerializedName("fingerName")
    private String FingerName;

    public FingurePrintModel(String fingerPrint, String idNo,String machineId,String agentId,String fingerName) {
        FingerPrint = fingerPrint;
        IdNo = idNo;
        MachineId=machineId;
        AgentId=agentId;
        FingerName = fingerName;
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

    public String getmachineId() {
        return MachineId;
    }
    public void setmachineId(String machineId) {
        MachineId = machineId;
    }

    public String getAgentId() {
        return AgentId;
    }
    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public String getFingerName() {
        return FingerName;
    }
    public void setFingerName(String fingerName) {
        FingerName = fingerName;
    }
}
