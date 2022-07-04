package com.ironhack.midterm.controller.dto;

import java.util.Date;

public class DateOfBirthDTO {
    private Date dateOfBirth;

    public DateOfBirthDTO() {
    }

    public DateOfBirthDTO(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
