package com.berkantcanerkanat.cuisine.JSONResponse;

import com.berkantcanerkanat.cuisine.Models.MealCategory;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONResponseForCategories {
    @SerializedName("categories")
    private ArrayList<MealCategory> categories;

    public ArrayList<MealCategory> getCategories() {
        return categories;
    }
}
