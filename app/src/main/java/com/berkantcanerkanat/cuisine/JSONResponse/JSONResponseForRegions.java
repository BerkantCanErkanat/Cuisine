package com.berkantcanerkanat.cuisine.JSONResponse;

import com.berkantcanerkanat.cuisine.Models.Region;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONResponseForRegions {

    @SerializedName("meals")
    private ArrayList<Region> regions;

    public ArrayList<Region> getRegions() {
        return regions;
    }
}
