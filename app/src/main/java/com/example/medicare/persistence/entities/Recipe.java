package com.example.medicare.persistence.entities;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String writeBy, writeFor;
    private List<Medication> medication;

    public Recipe(String writeBy, String writeFor) {
        this.writeBy = writeBy;
        this.writeFor = writeFor;
        this.medication = new ArrayList<>();
    }

    public String getWriteBy() {
        return writeBy;
    }

    public void setWriteBy(String writeBy) {
        this.writeBy = writeBy;
    }

    public String getWriteFor() {
        return writeFor;
    }

    public void setWriteFor(String writeFor) {
        this.writeFor = writeFor;
    }

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
    }

    public void addMedicationToRecipe(Medication medication) {
        this.medication.add(medication);
    }
}
