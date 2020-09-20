package com.aratek.trustfinger.Model;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("ProductID")
    private String productID;
    @SerializedName("Description")
    private String description;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
