package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class AccountModel {
    @SerializedName("AccountNo")
    private String AccountNo;

    public AccountModel(String AccountNo) {
        this.AccountNo = AccountNo;
    }
    public String getAccountNo() { return AccountNo; }

    public void setAccountNo(String accountNo) { AccountNo = accountNo; }



}
