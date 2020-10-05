package com.example.medicare.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class ManagePrescriptionFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ListView lv_prescriptions;
    static String[] drugs;
    static String[] hours;
    static String selection;
    static ArrayList<String> al_drugs = new ArrayList<>();
    static ArrayList<String> al_hours = new ArrayList<>();
    static ProgressBar progressBar;

    public ManagePrescriptionFragment() {

    }

    public static ManagePrescriptionFragment newInstance() {
        ManagePrescriptionFragment fragment = new ManagePrescriptionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewMain = inflater.inflate(R.layout.fragment_manage_prescription, container, false);


        populatePrescriptionsListView(viewMain);

        progressBar = viewMain.findViewById(R.id.pb_prescriptions);
        progressBar.setVisibility(View.VISIBLE);



        return viewMain;
    }


    public void populatePrescriptionsListView(final View view){
        al_hours.clear();
        al_drugs.clear();
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
                            db.collection("prescriptions")
                                    .whereEqualTo("patient", myName[0])
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    al_drugs.add(document.get("drug").toString());
                                                    al_hours.add(document.get("hour").toString());
                                                }
                                                getAppointments(view, al_drugs, al_hours);
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

    public void getAppointments(View view, ArrayList<String> al_drugs, ArrayList<String> al_hours){
        lv_prescriptions = view.findViewById(R.id.list_view_prescriptions);
        drugs = new String[al_drugs.size()];
        hours = new String[al_hours.size()];
        al_drugs.toArray(drugs);
        al_hours.toArray(hours);
        //ArrayAdapter<String> monthAdaptor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, appointments);
        MyAdapter adapter = new MyAdapter(getContext(), drugs, hours);
        //lv_appointments.setAdapter(monthAdaptor);
        lv_prescriptions.setAdapter(adapter);
        //lv_appointments.setOnItemClickListener(this);
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