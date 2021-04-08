package com.berkantcanerkanat.cuisine.Models;

import com.google.gson.annotations.SerializedName;

public class Region {

    @SerializedName("strArea")
    private String strArea;

    public String getStrArea() {
        return strArea;
    }
}
