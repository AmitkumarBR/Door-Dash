package com.example.doordashuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordash.AddFoodHelperClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodDetailsAdapter extends RecyclerView.Adapter<FoodDetailsAdapter.ViewHolder> {

    Context context;
    List<AddFoodHelperClass> foodList;

    public FoodDetailsAdapter(Context context, List<AddFoodHelperClass> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_details_recycler_view_design, parent, false);
        return new FoodDetailsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDetailsAdapter.ViewHolder holder, int position) {
        AddFoodHelperClass addFoodHelperClass = foodList.get(position);
        holder.foodName.setText("" + addFoodHelperClass.getFoodName());
        holder.foodDescription.setText("" + addFoodHelperClass.getFoodDesc());

        String imageUri = null;
        imageUri = addFoodHelperClass.getFoodImage();
        Picasso.get().load(imageUri).into(holder.foodImage);

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage;
        TextView foodName, foodDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodDescription = itemView.findViewById(R.id.food_description);
        }
    }
}
