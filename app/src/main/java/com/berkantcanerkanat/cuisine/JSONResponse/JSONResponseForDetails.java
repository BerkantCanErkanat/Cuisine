package com.berkantcanerkanat.cuisine.JSONResponse;

import com.berkantcanerkanat.cuisine.Models.MealDetail;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONResponseForDetails {

    @SerializedName("meals")
    private ArrayList<MealDetail> mealDetails;

    public ArrayList<MealDetail> getMealDetails() {
        return mealDetails;
    }
}
