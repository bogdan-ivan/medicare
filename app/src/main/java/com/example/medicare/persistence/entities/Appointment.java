package com.example.medicare.persistence.entities;

import com.example.medicare.enums.AppointmentStatus;

public class Appointment {
    private String date, startTime, endTime, patientId, doctorId;
    private AppointmentStatus status;

    public Appointment(String patientId, String doctorId, String date, String startTime, String endTime, AppointmentStatus status) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
