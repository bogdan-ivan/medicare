package com.example.medicare.persistence.entities;

import com.example.medicare.enums.ApplicationRoles;
import com.example.medicare.persistence.entities.generics.AbstractUser;

public class Admin extends AbstractUser {

    public Admin(String name, String email, ApplicationRoles  role) {
        super(name, email, role);
    }

}
