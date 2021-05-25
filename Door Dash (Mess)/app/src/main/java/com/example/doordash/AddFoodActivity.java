package com.example.doordash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFoodActivity extends AppCompatActivity {
    private ImageView addFoodImage;
    private Button addFoodButton;
    private TextInputLayout addFoodName, addFoodPrice, addFoodDesc;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;

    static final int Gallery_Code = 1;
    private Uri imageUrl = null;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        init();
    }

    private void init() {
        addFoodImage = (ImageView) findViewById(R.id.addFood_image);
        addFoodName = (TextInputLayout) findViewById(R.id.addFood_name);
        addFoodPrice = (TextInputLayout) findViewById(R.id.addFood_price);
        addFoodDesc = (TextInputLayout) findViewById(R.id.addFood_desc);
        addFoodButton = (Button) findViewById(R.id.addFood_button);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Food Details");
        mStorage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);

        addFoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Code && resultCode == RESULT_OK) {
            imageUrl = data.getData();
            addFoodImage.setImageURI(imageUrl);
        }

        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                String name = addFoodName.getEditText().getText().toString();
                String price = addFoodPrice.getEditText().getText().toString();
                String description = addFoodDesc.getEditText().getText().toString();

                if (!(name.isEmpty() && price.isEmpty() && description.isEmpty() && imageUrl != null)) {
                    StorageReference filepath = mStorage.getReference().child("ImagePost").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    DatabaseReference newPost = mRef.child(name);
                                    newPost.child("foodName").setValue(name);
                                    newPost.child("foodPrice").setValue(price);
                                    newPost.child("foodDesc").setValue(description);
                                    newPost.child("foodImage").setValue(task.getResult().toString());
                                    progressDialog.dismiss();
                                    startActivity(new Intent(AddFoodActivity.this, AddFoodActivity.class));
                                }
                            });
                        }
                    });
                }

            }
        });
    }
}