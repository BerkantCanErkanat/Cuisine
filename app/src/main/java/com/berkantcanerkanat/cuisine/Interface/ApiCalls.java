package com.berkantcanerkanat.cuisine.Interface;

import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForCategories;
import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForDetails;
import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForMeals;
import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForRegions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiCalls {


   @GET("list.php?a=list")
    Call<JSONResponseForRegions> getRegions();

   @GET("categories.php")
    Call<JSONResponseForCategories> getCategories();

   @GET()
    Call<JSONResponseForMeals> getMealsWithRegion(
           @Url String url);

    @GET()
    Call<JSONResponseForMeals> getMealsWithCategory(
            @Url String url);

    @GET()
    Call<JSONResponseForDetails> getDetails(
            @Url String url);

}
