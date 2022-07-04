package com.ironhack.midterm.model;

import javax.persistence.*;

@Entity
@Table(name = "`admin`")
@PrimaryKeyJoinColumn(name = "admin_id")
public class Admin extends User {
    private String name;

    public Admin() {
    }

    public Admin(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
