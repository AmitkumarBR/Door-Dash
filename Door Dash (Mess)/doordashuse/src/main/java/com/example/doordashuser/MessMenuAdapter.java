package com.example.doordashuser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordash.AddMessHelperClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MessMenuAdapter extends RecyclerView.Adapter<MessMenuAdapter.ViewHolder> {

    Context context;
    List<AddMessHelperClass> messList;
    List<AddMessHelperClass> searchList;

    public MessMenuAdapter(Context context, List<AddMessHelperClass> messList) {
        this.context = context;
        this.messList = messList;
//        searchList = new ArrayList<>(searchList);
    }

    @NonNull
    @Override
    public MessMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_menu_recycler_view_design, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessMenuAdapter.ViewHolder holder, int position) {
        AddMessHelperClass addMessHelperClass = messList.get(position);
        holder.messName.setText("" + addMessHelperClass.getMessName());
        holder.messRatings.setText("" + addMessHelperClass.getMessRatings());

        String imageUri = null;
        imageUri = addMessHelperClass.getMessImage();
        Picasso.get().load(imageUri).into(holder.messImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), FoodDetailsActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView messImage;
        TextView messName, messRatings;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messImage = itemView.findViewById(R.id.mess_image);
            messName = itemView.findViewById(R.id.mess_name);
            messRatings = itemView.findViewById(R.id.mess_ratings);
            cardView = itemView.findViewById(R.id.card_view);

        }
    }
}
