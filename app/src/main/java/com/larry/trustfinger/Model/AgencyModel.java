package com.larry.trustfinger.Model;
import com.google.gson.annotations.SerializedName;

public class AgencyModel {
    @SerializedName("names")
    private String names;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("idno")
    private String idno;
    @SerializedName("phone")
    private String phone;
    @SerializedName("machineId")
    private String machineId;
    @SerializedName("agency")
    private String agency;
    @SerializedName("admins")
    private String admins;
    @SerializedName("agentid")
    private String agentid;
    @SerializedName("Fingerprint")
    private String Fingerprint;

    public AgencyModel(String names,String lastname, String idno,String phone,String machineId,String admins,String agency,String agentid,String Fingerprint) {
        this.names = names;
        this.lastname = lastname;
        this.idno = idno;
        this.phone = phone;
        this.machineId = machineId;
        this.admins = admins;
        this.agency = agency;
        this.agentid = agentid;
        this.Fingerprint = Fingerprint;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }




    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getAdmins() {
        return admins;
    }

    public void setAdmins(String admins) {
        this.admins = admins;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getFingerprint() {
        return Fingerprint;
    }
    public void setFingerprint(String Fingerprint) { this.Fingerprint = Fingerprint;
    }
}


