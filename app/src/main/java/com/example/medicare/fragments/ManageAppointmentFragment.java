package com.example.medicare.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormatSymbols;
import java.util.ArrayList;


public class ManageAppointmentFragment extends Fragment implements AdapterView.OnItemClickListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ListView lv_appointments;
    static String[] doctors;
    static String[] dates;
    static String selection;
    static ArrayList<String> al_doctors = new ArrayList<>();
    static ArrayList<String> al_dates = new ArrayList<>();
    static ProgressBar progressBar;

    public ManageAppointmentFragment() {

    }

    public static ManageAppointmentFragment newInstance() {
        ManageAppointmentFragment fragment = new ManageAppointmentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void populateAppointmentsListView(final View view){
        al_dates.clear();
        al_doctors.clear();
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
                            db.collection("appointments")
                                    .whereEqualTo("patient", myName[0])
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    al_doctors.add(document.get("doctor").toString());
                                                    al_dates.add(document.get("date").toString());
                                                }
                                                getAppointments(view, al_doctors, al_dates);
                                                progressBar.setVisibility(View.INVISIBLE);
                                            } else {

                                            }
                                        }
                                    });
                        } else {

                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View viewMain = inflater.inflate(R.layout.fragment_manage_appointment, container, false);



        populateAppointmentsListView(viewMain);

        Button delete_appointment = viewMain.findViewById(R.id.btn_delete_appointment);
        progressBar = viewMain.findViewById(R.id.pb_appointments);
        progressBar.setVisibility(View.VISIBLE);
        delete_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("appointments")
                        .whereEqualTo("date", selection)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Appointment Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getActivity(), "Appointment Was Not Deleted", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } else {

                                }
                            }
                        });

                populateAppointmentsListView(viewMain);
            }
        });

        return viewMain;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selection = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getActivity(), selection + " selected", Toast.LENGTH_SHORT).show();
    }

    public void getAppointments(View view, ArrayList<String> al_doctors, ArrayList<String> al_dates){
        lv_appointments = view.findViewById(R.id.list_view_appointments);
        doctors = new String[al_doctors.size()];
        dates = new String[al_dates.size()];
        al_doctors.toArray(doctors);
        al_dates.toArray(dates);
        //ArrayAdapter<String> monthAdaptor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, appointments);
        MyAdapter adapter = new MyAdapter(getContext(), dates, doctors);
        //lv_appointments.setAdapter(monthAdaptor);
        lv_appointments.setAdapter(adapter);
        lv_appointments.setOnItemClickListener(this);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];

        MyAdapter (Context c, String title[], String description[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }

}