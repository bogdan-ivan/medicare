package com.example.medicare.persistence.entities;

import com.example.medicare.enums.ApplicationRoles;
import com.example.medicare.persistence.entities.generics.AbstractUser;

import java.util.ArrayList;
import java.util.List;

public class Patient extends AbstractUser {

    private List<Appointment> appointmentList;
    private List<Recipe> recipeList;
    private boolean seriouslyIll;


    public Patient(String name, String email, ApplicationRoles role) {
        super(name, email, role);
        this.appointmentList = new ArrayList<>();
        this.recipeList = new ArrayList<>();
        this.seriouslyIll = false;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public boolean isSeriouslyIll() {
        return seriouslyIll;
    }

    public void setSeriouslyIll(boolean seriouslyIll) {
        this.seriouslyIll = seriouslyIll;
    }
}
