package com.berkantcanerkanat.cuisine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.berkantcanerkanat.cuisine.DataBase.DatabaseOperations;
import com.berkantcanerkanat.cuisine.Interface.ApiCalls;
import com.berkantcanerkanat.cuisine.JSONResponse.JSONResponseForDetails;
import com.berkantcanerkanat.cuisine.Models.MealDetail;
import com.berkantcanerkanat.cuisine.Singleton.User;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealDetailActivity extends AppCompatActivity {
    String mealId;
    User user;
    TextView toolbarTitle;
    ImageView toolbar_back;
    LoadingDialog loadingDialog;
    Retrofit retrofit;
    ApiCalls myApiCalls;
    DatabaseOperations db;
    ImageView mealPicture,starPicture;
    TextView title,category,description;
    //https://www.themealdb.com/api/json/v1/1/lookup.php?i=52767


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);


        //intent
        Intent intent = getIntent();
        mealId = intent.getStringExtra("mealId");

        //user singleton
        user = User.getInstance();

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
        loadingDialog = new LoadingDialog(MealDetailActivity.this);

        //views
        title = findViewById(R.id.productDetailTitle);
        mealPicture = findViewById(R.id.thumbView);
        category = findViewById(R.id.productDetailCategory);
        description = findViewById(R.id.productDetailDescription);
        starPicture = findViewById(R.id.productDetailStar);


        //starPicture initialize
        if(user.getIds().contains(mealId)){
            starPicture.setImageResource(R.drawable.full_star);
        }

        //star click listener
        starPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getIds().contains(mealId)){
                    starPicture.setImageResource(R.drawable.empty_star);
                    user.getIds().remove(mealId);
                }else{
                    starPicture.setImageResource(R.drawable.full_star);
                    user.getIds().add(mealId);
                }
            }
        });

        //retrofit
        buildRetrofit();

        //api interface connection
        if(retrofit != null){
            myApiCalls = retrofit.create(ApiCalls.class);
        }

        //fetching the data
        fetchtheDetails();


    }
    @Override
    protected void onPause() {
        super.onPause();
        //when we go to another activity or close this activity
        //this method runs
        //so I choose this method to deal with database operation
        db = new DatabaseOperations(getApplicationContext());
        db.putTheIds();
    }
    private void fetchtheDetails(){
        if(myApiCalls != null){
            String url = "lookup.php?i=" + mealId;
            Call<JSONResponseForDetails> jsonResponse = myApiCalls.getDetails(url);

            //loading dialog
            loadingDialog.startLoadingDialog();

            jsonResponse.enqueue(new Callback<JSONResponseForDetails>() {
                @Override
                public void onResponse(Call<JSONResponseForDetails> call, Response<JSONResponseForDetails> response) {
                    if(response.isSuccessful()){
                        MealDetail mealDetail = response.body().getMealDetails().get(0);
                        toolbarTitle.setText(mealDetail.getStrMeal());
                        title.setText(mealDetail.getStrMeal());
                        category.setText("Category : "+mealDetail.getStrCategory());
                        description.setText(mealDetail.getStrInstructions());

                        Glide.with(MealDetailActivity.this)
                                .load(mealDetail.getStrMealThumb())
                                .into(mealPicture);

                        //dismiss loading dialog
                        loadingDialog.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<JSONResponseForDetails> call, Throwable t) {

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