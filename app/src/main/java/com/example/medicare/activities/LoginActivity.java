package com.example.medicare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicare.R;
import com.example.medicare.enums.ApplicationRoles;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn, mRegisterBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        progressBar = findViewById(R.id.loginProgressBar);
        mLoginBtn = findViewById(R.id.loginButton);
        mRegisterBtn = findViewById(R.id.goToRegister);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            FirebaseFirestore.getInstance().collection("users")
                    .whereEqualTo("email", fAuth.getCurrentUser().getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    MainActivity.userName = (String) document.get("fullName");
                                    String r = document.get("Role").toString();
                                    MainActivity.userRole = ApplicationRoles.valueOf(document.get("Role").toString());
                                }
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }
                    });
            finish();
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //TO DO: Add regex email validation
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("You need to provide an email!");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("You must set a password!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "User logged in successfully!", Toast.LENGTH_SHORT).show();
                            MainActivity.userName = task.getResult().getUser().getEmail();
                            FirebaseFirestore.getInstance().collection("users")
                                    .whereEqualTo("email", task.getResult().getUser().getEmail())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    MainActivity.userName = (String) document.get("fullName");
                                                    String r = document.get("Role").toString();
                                                    MainActivity.userRole = ApplicationRoles.valueOf(document.get("Role").toString());
                                                }
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(LoginActivity.this, "Error !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }
}