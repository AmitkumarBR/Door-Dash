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

import java.util.UUID;

public class AddMessDetailsActivity extends AppCompatActivity {

    private ImageView messImage;
    private Button btnAddMess;
    private TextInputLayout messName, messPhone, messRatings;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;

    static final int Gallery_Code = 1;
    private Uri imageUrl = null;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mess_details);

        init();
    }

    private void init() {

        messImage = findViewById(R.id.mess_image);
        messName = findViewById(R.id.mess_name);
        messPhone = findViewById(R.id.mess_phone);
        messRatings = findViewById(R.id.mess_ratings);
        btnAddMess = findViewById(R.id.btn_add_mess);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Mess Details");
        mStorage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);

        messImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });

//        btnAddMess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AddMessDetailsActivity.this, AddFoodActivity.class));
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Code && resultCode == RESULT_OK) {
            imageUrl = data.getData();
            messImage.setImageURI(imageUrl);
        }

        btnAddMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                String name = messName.getEditText().getText().toString();
                String phone = messPhone.getEditText().getText().toString();
                String ratings = messRatings.getEditText().getText().toString();

                if (!(name.isEmpty() && ratings.isEmpty() && imageUrl != null)) {
                    StorageReference filepath = mStorage.getReference().child("ImagePost").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    DatabaseReference newPost = mRef.child(phone);
                                    newPost.child("messName").setValue(name);
                                    newPost.child("messPhone").setValue(phone);
                                    newPost.child("messRatings").setValue(ratings);
                                    newPost.child("messImage").setValue(task.getResult().toString());
                                    progressDialog.dismiss();
                                    startActivity(new Intent(AddMessDetailsActivity.this, AddFoodActivity.class));
                                }
                            });
                        }
                    });
                }

            }
        });
    }
}