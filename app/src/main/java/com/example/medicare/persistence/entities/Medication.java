package com.example.medicare.persistence.entities;

public class Medication {

    private String name;
    private int quantity;
    private int idealQuantity;
    private String leaflet;

    public Medication(String name, int quantity, int idealQuantity, String leaflet) {
        this.name = name;
        this.quantity = quantity;
        this.idealQuantity = idealQuantity;
        this.leaflet = leaflet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdealQuantity() {
        return idealQuantity;
    }

    public void setIdealQuantity(int idealQuantity) {
        this.idealQuantity = idealQuantity;
    }

    public String getLeaflet() {
        return leaflet;
    }

    public void setLeaflet(String leaflet) {
        this.leaflet = leaflet;
    }
}
