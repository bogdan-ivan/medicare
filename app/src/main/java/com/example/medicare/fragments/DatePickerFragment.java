package com.example.medicare.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;

import com.example.medicare.R;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        //FragmentManager fragmentManager = getActivity().getFragmentManager();
        //Fragment currentFragment = (Fragment) fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
        //Fragment currentFragment = fm.findFragmentByTag("TAG_FRAGMENT_ADD_APPOINTMENT");
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_appointments);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) currentFragment, year, month, day);
    }
}