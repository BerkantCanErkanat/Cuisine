package com.berkantcanerkanat.cuisine.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berkantcanerkanat.cuisine.Models.Meal;
import com.berkantcanerkanat.cuisine.MealDetailActivity;
import com.berkantcanerkanat.cuisine.R;
import com.berkantcanerkanat.cuisine.Singleton.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.myViewHolder> {
   Context context;
   ArrayList<Meal> meals;
   User user;
    public MealAdapter(Context context, ArrayList<Meal> meals) {
        this.context = context;
        this.meals = meals;
        user = User.getInstance();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.layout_meal_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
       holder.title.setText(meals.get(position).getStrMeal());

       //Glide
        Glide.with(context)
                .load(meals.get(position).getStrMealThumb())
                .into(holder.mealImage);

        //star image
        if(user.getIds().contains(String.valueOf(meals.get(position).getIdMeal()))){ //fav meal
            holder.star.setImageResource(R.drawable.full_star);
        }

        //star click listener
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getIds().contains(String.valueOf(meals.get(position).getIdMeal()))){ //fav meal
                    holder.star.setImageResource(R.drawable.empty_star);
                    user.getIds().remove(meals.get(position).getIdMeal());
                }else{
                    holder.star.setImageResource(R.drawable.full_star);
                    user.getIds().add(meals.get(position).getIdMeal());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealDetailActivity.class);
                intent.putExtra("mealId",meals.get(position).getIdMeal());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        ImageView star;
        ImageView mealImage;
        TextView title;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            star = itemView.findViewById(R.id.mealStar);
            mealImage = itemView.findViewById(R.id.mealImageView);
            title = itemView.findViewById(R.id.mealTitle);
        }
    }
}
