package com.example.medicare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicare.R;
import com.example.medicare.enums.ApplicationRoles;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

        public static String userName;
        public static ApplicationRoles userRole;
        TextView welcomeMessage;
        Button manageAccountBtn, createAccountBtn, appointmentsBtn, prescriptionsBtn, medicineSuppliesBtn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            welcomeMessage = findViewById(R.id.welcomeMessage);
            welcomeMessage.setText("Welcome " + userName);
            setButtonsVisibilityDepindingOnRole();
            Button appointmentsMenu = findViewById(R.id.btn_appointments_menu);
            Button prescriptionsMenu = findViewById(R.id.btn_prescriptions_menu);
            appointmentsMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), AppointmentActivity.class));
                }
            });
            prescriptionsMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), PrescriptionActivity.class));
                }
            });

        }

        private void setButtonsVisibilityDepindingOnRole() {
            if(userRole != null) {
                manageAccountBtn = findViewById(R.id.manageAccounts);
                manageAccountBtn.setVisibility(View.GONE);
                createAccountBtn = findViewById(R.id.createAccountButton);
                createAccountBtn.setVisibility(View.GONE);
                appointmentsBtn = findViewById(R.id.btn_appointments_menu);
                appointmentsBtn.setVisibility(View.GONE);
                prescriptionsBtn = findViewById(R.id.btn_prescriptions_menu);
                prescriptionsBtn.setVisibility(View.GONE);
//                medicineSuppliesBtn = findViewById(R.id.medicineSuppliesButton);
//                medicineSuppliesBtn.setVisibility(View.GONE);

                switch (userRole) {
                    case ADMIN:
                        manageAccountBtn.setVisibility(View.VISIBLE);
                        createAccountBtn.setVisibility(View.VISIBLE);
                        break;
                    case HOSPITAL_MANAGER:
                        createAccountBtn.setVisibility(View.VISIBLE);
                        break;
                    case DOCTOR:
                        appointmentsBtn.setVisibility(View.VISIBLE);
                        prescriptionsBtn.setVisibility(View.VISIBLE);
                        break;
                    case PATIENT:
                        appointmentsBtn.setVisibility(View.VISIBLE);
                        prescriptionsBtn.setVisibility(View.VISIBLE);
                        break;
                    case NURSE:
                        medicineSuppliesBtn.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }

        public void logout(View view) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
}
