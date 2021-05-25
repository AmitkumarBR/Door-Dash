package com.example.doordashuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.doordash.AddFoodHelperClass;
import com.example.doordash.AddMessHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private FoodDetailsAdapter foodDetailsAdapter;
    List<AddFoodHelperClass> foodDetailsList;

    private FirebaseAuth Auth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        
        init();
    }

    private void init() {

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Food Details");
        mStorage = FirebaseStorage.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodDetailsList = new ArrayList<AddFoodHelperClass>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot newSnapshot : snapshot.getChildren()) {
                        AddFoodHelperClass food = newSnapshot.getValue(AddFoodHelperClass.class);
                        foodDetailsList.add(food);
                    }
                    foodDetailsAdapter = new FoodDetailsAdapter(FoodDetailsActivity.this, foodDetailsList);
                    recyclerView.setAdapter(foodDetailsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}