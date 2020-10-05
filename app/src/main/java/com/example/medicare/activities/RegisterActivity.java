package com.example.medicare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicare.R;
import com.example.medicare.enums.ApplicationRoles;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName, mEmail, mPhone, mCNP, mPassword, mConfirmPassword;
    Button mRegisterButton;
    TextView mLoginButton;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.userFullName);
        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        mConfirmPassword = findViewById(R.id.userConfirmPassword);
        mPhone = findViewById(R.id.userPhoneNumber);
        mCNP = findViewById(R.id.userCNP);
        mRegisterButton = findViewById(R.id.registerButton);
        mLoginButton = findViewById(R.id.goToLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.registerProgressBar);

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }



        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                final String phone = mPhone.getText().toString().trim();
                final String name = mFullName.getText().toString();
                final String CNP = mCNP.getText().toString().trim();
                final ApplicationRoles role = ApplicationRoles.PATIENT;

                if(TextUtils.isEmpty(name)) {
                    mEmail.setError("You need to provide a name!");
                    return;
                }

                //TO DO: Add regex email validation
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("You need to provide an email!");
                    return;
                }

                if(TextUtils.isEmpty(phone)) {
                    mEmail.setError("You need to provide a phone number!");
                    return;
                }

                if(TextUtils.isEmpty(CNP)) {
                    mEmail.setError("You need to provide the CNP!");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("You must set a password!");
                    return;
                }

                if(password.length() < 6) {
                    mPassword.setError("Password should be at least 6 characters long!");
                    return;
                }

                if(CNP.length() < 13) {
                    mCNP.setError("CNP should be at least 13 characters long!");
                    return;
                }

                if(phone.length() < 10) {
                    mPhone.setError("Phone number should be at least 10 characters long!");
                    return;
                }

                if(TextUtils.isEmpty(confirmPassword)) {
                    mConfirmPassword.setError("You must retype the password here!");
                    return;
                }

                if(!confirmPassword.equals(password)) {
                    mConfirmPassword.setError("The passwords are not the same!");
                    mConfirmPassword.setText("");
                    return;
                }

                MainActivity.userName = name;

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fullName", name);
                            user.put("email", email);
                            user.put("phoneNumber", phone);
                            user.put("CNP", CNP);
                            user.put("Role", role);
                            user.put("password", Base64.encode(password.getBytes(), 0).toString());
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("INFO: ", "User profile created for " + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

}