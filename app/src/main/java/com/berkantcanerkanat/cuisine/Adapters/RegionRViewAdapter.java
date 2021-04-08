package com.berkantcanerkanat.cuisine.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berkantcanerkanat.cuisine.MealListActivity;
import com.berkantcanerkanat.cuisine.R;
import com.berkantcanerkanat.cuisine.Models.Region;

import java.util.ArrayList;

public class RegionRViewAdapter extends RecyclerView.Adapter<RegionRViewAdapter.myViewHolder> {
   Context context;
   ArrayList<Region> regions;

    public RegionRViewAdapter(Context context, ArrayList<Region> regions) {
        this.context = context;
        this.regions = regions;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.layout_region_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
     holder.region.setText(regions.get(position).getStrArea());
     holder.region.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            //transition to MealList Activity
             Intent intent = new Intent(context, MealListActivity.class);
             intent.putExtra("from","region");
             intent.putExtra("strArea",regions.get(position).getStrArea());
             context.startActivity(intent);
         }
     });
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView region;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            region = itemView.findViewById(R.id.region_item_TextView);
        }
    }
}
