package com.berkantcanerkanat.cuisine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.berkantcanerkanat.cuisine.Adapters.MealAdapter;
import com.berkantcanerkanat.cuisine.DataBase.DatabaseOperations;
import com.berkantcanerkanat.cuisine.Interface.ApiCalls;
import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForMeals;
import com.berkantcanerkanat.cuisine.Models.Meal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealListActivity extends AppCompatActivity{
    String strArea = null;
    String strCategory = null;
    TextView toolbarTitle;
    ImageView toolbar_back;
    LoadingDialog loadingDialog;
    Retrofit retrofit;
    ApiCalls myApiCalls;
    ArrayList<Meal> meals;
    RecyclerView recyclerView;
    DatabaseOperations db;


    @Override
    protected void onPause() {
        super.onPause();
        //when we go to another activity or close this activity
        //this method runs
        //so I choose this method to deal with database operation
        db = new DatabaseOperations(getApplicationContext());
        db.putTheIds();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if we add a fav in detail activity it does not show
        //so I adjust the recycler view to fresh again
        adjustTheRecyclerView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);


        //recyclerview
        recyclerView = findViewById(R.id.mealRecyclerView);

        //toolbar
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbar_back = findViewById(R.id.back_icon);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        //loading dialog
        loadingDialog = new LoadingDialog(MealListActivity.this);

        //retrofit
        buildRetrofit();

        //api interface connection
        if(retrofit != null){
            myApiCalls = retrofit.create(ApiCalls.class);
        }

        fromWhichActivityImComing();


    }
    private void adjustTheRecyclerView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MealListActivity.this,2,RecyclerView.VERTICAL,false);
        if(meals != null){
            MealAdapter mealAdapter = new MealAdapter(MealListActivity.this,meals);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(mealAdapter);
        }
    }
    private void fetchDataWithRegion(){
        if(myApiCalls != null){
            String url = "filter.php?a=" + strArea;
            Call<JSONResponseForMeals> jsonResponse = myApiCalls.getMealsWithRegion(url);

            //loading dialog
            loadingDialog.startLoadingDialog();

            jsonResponse.enqueue(new Callback<JSONResponseForMeals>() {
                @Override
                public void onResponse(Call<JSONResponseForMeals> call, Response<JSONResponseForMeals> response) {
                  if(response != null){
                      meals = new ArrayList<>(response.body().getMeals());
                      adjustTheRecyclerView();

                      //dismiss dialog
                      loadingDialog.dismissDialog();
                  }

                }

                @Override
                public void onFailure(Call<JSONResponseForMeals> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }

    }
    private void fetchDataWithCategory(){
        if(myApiCalls != null){
            String url = "filter.php?c=" + strCategory;
            Call<JSONResponseForMeals> jsonResponse = myApiCalls.getMealsWithCategory(url);

            //loading dialog
            loadingDialog.startLoadingDialog();

            jsonResponse.enqueue(new Callback<JSONResponseForMeals>() {
                @Override
                public void onResponse(Call<JSONResponseForMeals> call, Response<JSONResponseForMeals> response) {
                    if(response != null){
                        meals = new ArrayList<>(response.body().getMeals());
                        adjustTheRecyclerView();

                        //dismiss dialog
                        loadingDialog.dismissDialog();
                    }
                }
                @Override
                public void onFailure(Call<JSONResponseForMeals> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }
    }
    private void buildRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void fromWhichActivityImComing(){
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");

        if(from.equals("region")){
            strArea = intent.getStringExtra("strArea");
            if(strArea != null) toolbarTitle.setText(strArea);

            fetchDataWithRegion();

        }else if(from.equals("category")){
            strCategory = intent.getStringExtra("strcategory");
            if(strCategory != null) toolbarTitle.setText(strCategory);

            fetchDataWithCategory();
        }
    }
}