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

import com.berkantcanerkanat.cuisine.Models.MealCategory;
import com.berkantcanerkanat.cuisine.MealListActivity;
import com.berkantcanerkanat.cuisine.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryRViewAdapter extends RecyclerView.Adapter<CategoryRViewAdapter.myViewHolder> {
   Context context;
   ArrayList<MealCategory> mealCategories;

    public CategoryRViewAdapter(Context context, ArrayList<MealCategory> mealCategories) {
        this.context = context;
        this.mealCategories = mealCategories;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.layout_category_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
      holder.categoryTitle.setText(mealCategories.get(position).getStrCategory());

        //glide
        Glide.with(context)
                .load(mealCategories.get(position).getStrCategoryThumb())
                .into(holder.mealImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealListActivity.class);
                intent.putExtra("from","category");
                intent.putExtra("strcategory",mealCategories.get(position).getStrCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealCategories.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
         ImageView mealImage;
         TextView categoryTitle;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.categoryItem_imageView);
            categoryTitle = itemView.findViewById(R.id.categoryItem_titleView);
        }
    }
}
