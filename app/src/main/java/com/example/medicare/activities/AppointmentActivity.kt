package com.example.medicare.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.medicare.R
import com.example.medicare.fragments.AddAppointmentFragment
import com.example.medicare.fragments.AppointmentFragment
import com.example.medicare.fragments.ManageAppointmentFragment
import com.example.medicare.persistence.entities.Appointment
import kotlinx.android.synthetic.main.fragment_appointment.*

class AppointmentActivity : AppCompatActivity() {

    companion object {
        const val TAG_FRAGMENT_MENU_APPOINTMENT = "TAG_FRAGMENT_MENU_APPOINTMENT"
        const val TAG_FRAGMENT_MANAGE_APPOINTMENT = "TAG_FRAGMENT_MANAGE_APPOINTMENT"
        const val TAG_FRAGMENT_ADD_APPOINTMENT = "TAG_FRAGMENT_ADD_APPOINTMENT"
        const val TAG_FRAGMENT_CHECK_CALENDAR = "TAG_FRAGMENT_CHECK_CALENDAR"
        //const val TAG_FRAGMENT_SHOW_APPOINTMENT = "TAG_FRAGMENT_SHOW_APPOINTMENT"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        replaceFragment(TAG_FRAGMENT_MENU_APPOINTMENT)

    }

    override fun onStart() {
        super.onStart()

    }

    
    fun replaceFragment(TAG: String) {
        TAG.run {
            val fragment = when(this){
                TAG_FRAGMENT_MENU_APPOINTMENT -> AppointmentFragment.newInstance()
                TAG_FRAGMENT_MANAGE_APPOINTMENT -> ManageAppointmentFragment.newInstance()
                TAG_FRAGMENT_ADD_APPOINTMENT -> AddAppointmentFragment.newInstance()
                //TAG_FRAGMENT_SHOW_APPOINTMENT -> LessonOneFragment.newInstance()

                else -> null
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment != null) {
                transaction.replace(R.id.fl_appointments, fragment, this )
            }
            if(this != TAG_FRAGMENT_MENU_APPOINTMENT) {
                transaction.addToBackStack(this)
            }
            transaction.commit()
        }
    }

}