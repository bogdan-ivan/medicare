package com.example.medicare.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicare.R
import com.example.medicare.fragments.AddPrescriptionFragment
import com.example.medicare.fragments.ManagePrescriptionFragment
import com.example.medicare.fragments.PrescriptionFragment

class PrescriptionActivity : AppCompatActivity() {

    companion object {
        const val TAG_FRAGMENT_MENU_PRESCRIPTION = "TAG_FRAGMENT_MENU_PRESCRIPTION"
        const val TAG_FRAGMENT_MANAGE_PRESCRIPTION = "TAG_FRAGMENT_MANAGE_PRESCRIPTION"
        const val TAG_FRAGMENT_ADD_PRESCRIPTION = "TAG_FRAGMENT_ADD_PRESCRIPTION"
        const val TAG_FRAGMENT_REMINDER_PRESCRIPTION = "TAG_FRAGMENT_REMINDER_PRESCRIPTION"
        //const val TAG_FRAGMENT_SHOW_APPOINTMENT = "TAG_FRAGMENT_SHOW_APPOINTMENT"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)
        replaceFragment(PrescriptionActivity.TAG_FRAGMENT_MENU_PRESCRIPTION)
        createNotificationChannel()
    }

    override fun onStart() {
        super.onStart()
    }

    fun replaceFragment(TAG: String) {
        TAG.run {
            val fragment = when(this){
                PrescriptionActivity.TAG_FRAGMENT_MENU_PRESCRIPTION -> PrescriptionFragment.newInstance()
                PrescriptionActivity.TAG_FRAGMENT_MANAGE_PRESCRIPTION -> ManagePrescriptionFragment.newInstance()
                PrescriptionActivity.TAG_FRAGMENT_ADD_PRESCRIPTION -> AddPrescriptionFragment.newInstance()
                //AppointmentActivity.TAG_FRAGMENT_REMINDER_PRESCRIPTION -> AddAppointmentFragment.newInstance()


                else -> null
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment != null) {
                transaction.replace(R.id.fl_prescriptions, fragment, this )
            }
            if(this != PrescriptionActivity.TAG_FRAGMENT_MENU_PRESCRIPTION) {
                transaction.addToBackStack(this)
            }
            transaction.commit()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "MediCareRemindChannel"
            val description = "Channel for MediCareReminder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel("notifyLemubit", name, importance)
            channel.description = description
            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}