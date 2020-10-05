package com.example.medicare.persistence.entities;

import com.example.medicare.enums.ApplicationRoles;
import com.example.medicare.enums.DoctorType;
import com.example.medicare.persistence.entities.generics.AbstractUser;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends AbstractUser {

    private List<Appointment> appointmentList;
    private DoctorType type;

    public Doctor(String name, String email, ApplicationRoles role) {
        super(name, email, role);
        this.appointmentList = new ArrayList<>();
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public DoctorType getType() {
        return type;
    }

    public void setType(DoctorType type) {
        this.type = type;
    }
}
