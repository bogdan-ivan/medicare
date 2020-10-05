package com.example.medicare.fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddPrescriptionFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    EditText mFullName, mDrug, mDoses;
    Button hourPickerPrescription, makePrescription;
    TextView tvHourPrescription;

    public AddPrescriptionFragment() {

    }

    public static AddPrescriptionFragment newInstance() {
        AddPrescriptionFragment fragment = new AddPrescriptionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);

        mFullName = view.findViewById(R.id.et_patient);
        mDrug = view.findViewById(R.id.et_drug);
        mDoses = view.findViewById(R.id.et_doses);
        makePrescription = view.findViewById(R.id.btn_make_prescription);
        hourPickerPrescription = view.findViewById(R.id.btn_hour_picker_prescription);
        tvHourPrescription = view.findViewById(R.id.tv_hour_prescription);



        final String[] myName = new String[1];
        String myEmail = fAuth.getCurrentUser().getEmail();
        db.collection("users")
                .whereEqualTo("email", myEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                myName[0] = (String) document.get("fullName");
                            }
                        } else {

                        }
                    }
                });

        makePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullName = mFullName.getText().toString().trim();
                final String drug = mDrug.getText().toString().trim();
                final int doses = Integer.parseInt(mDoses.getText().toString());
                final Map<String, Object> prescription = new HashMap<>();
                prescription.put("doctor", myName[0]);
                prescription.put("patient", fullName);
                prescription.put("drug", drug);
                prescription.put("dose", doses);
                prescription.put("hour", tvHourPrescription.getText().toString());

                db.collection("prescriptions")
                        .add(prescription)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getActivity(), "Prescription Created.", Toast.LENGTH_SHORT).show();
                                createAlarm("Drug Prescription", 12,30);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Prescription Was Not Created.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        hourPickerPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvHourPrescription.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });





        return view;
    }

    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}