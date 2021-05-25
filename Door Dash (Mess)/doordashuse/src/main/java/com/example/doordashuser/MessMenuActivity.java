package com.example.doordashuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.doordash.AddMessHelperClass;
import com.example.doordash.AdminHelperClass;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class MessMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private MessMenuAdapter messMenuAdapter;
    List<AddMessHelperClass> messMenuList;

    private FirebaseAuth Auth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);

        init();
    }

    private void init() {

//        searchView = findViewById(R.id.search_view);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Mess Details");
        mStorage = FirebaseStorage.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Context context;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(gridLayoutManager);

        messMenuList = new ArrayList<AddMessHelperClass>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot newSnapshot : snapshot.getChildren()) {
                        AddMessHelperClass user = newSnapshot.getValue(AddMessHelperClass.class);
                        messMenuList.add(user);
                    }
                    messMenuAdapter = new MessMenuAdapter(MessMenuActivity.this, messMenuList);
                    recyclerView.setAdapter(messMenuAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}