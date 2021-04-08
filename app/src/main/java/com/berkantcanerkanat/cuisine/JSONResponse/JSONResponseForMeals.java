package com.berkantcanerkanat.cuisine.JSONResponse;

import com.berkantcanerkanat.cuisine.Models.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONResponseForMeals {

    @SerializedName("meals")
    private ArrayList<Meal> meals;

    public ArrayList<Meal> getMeals() {
        return meals;
    }
}
