package com.berkantcanerkanat.cuisine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.berkantcanerkanat.cuisine.Adapters.CategoryRViewAdapter;
import com.berkantcanerkanat.cuisine.Adapters.RegionRViewAdapter;
import com.berkantcanerkanat.cuisine.DataBase.DatabaseOperations;
import com.berkantcanerkanat.cuisine.Interface.ApiCalls;
import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForCategories;
import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForRegions;
import com.berkantcanerkanat.cuisine.Models.MealCategory;
import com.berkantcanerkanat.cuisine.Models.Region;
import com.berkantcanerkanat.cuisine.Singleton.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //base =https://www.themealdb.com/api/json/v1/1/
    User user;
    ArrayList<Region> regions;
    ArrayList<MealCategory> mealCategories;
    Retrofit retrofit;
    ApiCalls myApiCalls;
    RecyclerView regionRecyclerView,categoryRecyclerView;
    ImageView toolbar_back;
    LoadingDialog loadingDialog;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //singleton object
        user = User.getInstance();

        //db operations
        DatabaseOperations db = new DatabaseOperations(getApplicationContext());
        db.getTheIds();


        //recycler views
        regionRecyclerView = findViewById(R.id.regionRView);
        categoryRecyclerView = findViewById(R.id.categoryRView);

        //loading dialog
        loadingDialog = new LoadingDialog(MainActivity.this);

        //toolbar
        toolbar_back = findViewById(R.id.back_icon);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onBackPressed();
            }
        });

        //retrofit
        buildRetrofit();

        //api interface connection
        if(retrofit != null){
            myApiCalls = retrofit.create(ApiCalls.class);
        }

        //regions (categories inside)
        getRegions();

    }

    private void getRegions(){
        if(myApiCalls != null){
            Call<JSONResponseForRegions> regionResponse = myApiCalls.getRegions();

            //loading started
            loadingDialog.startLoadingDialog();

            regionResponse.enqueue(new Callback<JSONResponseForRegions>() {
                @Override
                public void onResponse(Call<JSONResponseForRegions> call, Response<JSONResponseForRegions> response) {
                    if(response.isSuccessful()){
                        regions = new ArrayList<>(response.body().getRegions());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
                        regionRecyclerView.setLayoutManager(layoutManager);
                        RegionRViewAdapter regionRViewAdapter = new RegionRViewAdapter(MainActivity.this,regions);
                        regionRecyclerView.setAdapter(regionRViewAdapter);

                        //getCategories
                        getCategories();
                    }
                }

                @Override
                public void onFailure(Call<JSONResponseForRegions> call, Throwable t) {

                }
            });
         }
    }

    private void getCategories(){
        if(myApiCalls != null){
            Call<JSONResponseForCategories> categoryResponse = myApiCalls.getCategories();
            categoryResponse.enqueue(new Callback<JSONResponseForCategories>() {
                @Override
                public void onResponse(Call<JSONResponseForCategories> call, Response<JSONResponseForCategories> response) {
                    if(response.isSuccessful()){
                        mealCategories = new ArrayList<>(response.body().getCategories());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,3,RecyclerView.VERTICAL,false);
                        categoryRecyclerView.setLayoutManager(gridLayoutManager);
                        CategoryRViewAdapter categoryRViewAdapter = new CategoryRViewAdapter(MainActivity.this,mealCategories);
                        categoryRecyclerView.setAdapter(categoryRViewAdapter);

                        //loading dismissed

                        loadingDialog.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<JSONResponseForCategories> call, Throwable t) {

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
}