package com.example.medicare.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.medicare.R
import com.example.medicare.activities.PrescriptionActivity
import kotlinx.android.synthetic.main.fragment_prescription.*

class PrescriptionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prescription, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var fromFragmentToActivity = (activity as PrescriptionActivity?)

        btn_add_prescriptions.setOnClickListener() {
            fromFragmentToActivity?.replaceFragment(PrescriptionActivity.TAG_FRAGMENT_ADD_PRESCRIPTION)
        }

        btn_manage_prescriptions.setOnClickListener() {
            fromFragmentToActivity?.replaceFragment(PrescriptionActivity.TAG_FRAGMENT_MANAGE_PRESCRIPTION)
        }

        btn_reminders_prescriptions.setOnClickListener(){

        }


    }

    companion object {
        fun newInstance() = PrescriptionFragment()
    }


}