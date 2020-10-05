package com.example.medicare.persistence.entities.generics;

import com.example.medicare.enums.ApplicationRoles;

public abstract class AbstractUser {

    private String email;
    private String name;
    private ApplicationRoles role;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public ApplicationRoles getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(ApplicationRoles role) {
        this.role = role;
    }

    public AbstractUser(String name, String email, ApplicationRoles role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
