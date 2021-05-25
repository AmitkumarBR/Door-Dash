package com.example.doordash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout signUpFullName, signUpEmail, signUpPhone, signUpPassword;
    private Button signUpButton;
    private TextView signUpLoginTv;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        init();
    }

    private void init() {
        // Accessing XML Resources
        signUpFullName = (TextInputLayout) findViewById(R.id.signUp_fullName);
        signUpEmail = (TextInputLayout) findViewById(R.id.signUp_email);
        signUpPhone = (TextInputLayout) findViewById(R.id.signUp_phone);
        signUpPassword = (TextInputLayout) findViewById(R.id.signUp_password);
        signUpButton = (Button) findViewById(R.id.singUp_button);
        signUpLoginTv = (TextView) findViewById(R.id.signUpLogin_tv);

        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabaseRef = mFirebaseDatabase.getReference("Admins");

                // Get all the values
                String fullName = signUpFullName.getEditText().getText().toString();
                String email = signUpEmail.getEditText().getText().toString();
                String phone = signUpPhone.getEditText().getText().toString();
                String password = signUpPassword.getEditText().getText().toString();

                AdminHelperClass adminHelperClass = new AdminHelperClass(fullName, email, phone, password);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    Log.d("Msg", "onComplete" + task.getException());
                                }
                            }
                        });

//                int index = email.indexOf('@');
//                email = email.substring(0,index);

                mDatabaseRef.child(phone).setValue(adminHelperClass);
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        signUpLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}