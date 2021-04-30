package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class AdvanceLimitModel {
    @SerializedName("Accno")
    private String accno;
    @SerializedName("ProdID")
    private String prodID;



    public AdvanceLimitModel(String accno,String prodID) {

        this.accno = accno;
        this.prodID=prodID;

    }

    public String getAccno() {
        return accno;
    }
    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getProdID() {
        return prodID;
    }
    public void setProdID(String prodID) {
        this.prodID = prodID;
    }
}
