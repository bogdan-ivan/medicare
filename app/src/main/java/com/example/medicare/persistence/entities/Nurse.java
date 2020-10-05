package com.example.medicare.persistence.entities;

import com.example.medicare.enums.ApplicationRoles;
import com.example.medicare.persistence.entities.generics.AbstractUser;

public class Nurse extends AbstractUser {

    public Nurse(String name, String email, ApplicationRoles role) {
        super(name, email, role);
    }

}
