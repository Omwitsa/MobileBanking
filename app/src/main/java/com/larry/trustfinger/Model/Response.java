package com.larry.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("Success")
    private boolean success;
    @SerializedName("Message")
    private String message;
    @SerializedName("Data")
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
