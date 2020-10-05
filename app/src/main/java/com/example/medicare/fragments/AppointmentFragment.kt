package com.example.medicare.fragments

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.medicare.R
import com.example.medicare.activities.AppointmentActivity
import kotlinx.android.synthetic.main.fragment_appointment.*
import java.util.*


class AppointmentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var fromFragmentToActivity = (activity as AppointmentActivity?)

        btn_add_appointments.setOnClickListener() {
            fromFragmentToActivity?.replaceFragment(AppointmentActivity.TAG_FRAGMENT_ADD_APPOINTMENT)
        }

        btn_manage_appointments.setOnClickListener() {
            fromFragmentToActivity?.replaceFragment(AppointmentActivity.TAG_FRAGMENT_MANAGE_APPOINTMENT)
        }

        btn_check_calendar.setOnClickListener(){
            //fromFragmentToActivity?.replaceFragment(AppointmentActivity.TAG_FRAGMENT_CHECK_CALENDAR)

            val builder: Uri.Builder = CalendarContract.CONTENT_URI.buildUpon()
            builder.appendPath("time")
            ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis())
            val intent = Intent(Intent.ACTION_VIEW)
                .setData(builder.build())
            startActivity(intent)



        }
    }

    companion object {
        fun newInstance() = AppointmentFragment()
    }

}