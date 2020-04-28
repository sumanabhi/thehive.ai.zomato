package com.animusabhi.zomato_search.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offers {
    @SerializedName("offer")
    @Expose
    private Offer offer;

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

}
