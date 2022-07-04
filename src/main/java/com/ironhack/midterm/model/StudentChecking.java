package com.ironhack.midterm.model;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
@Entity
@PrimaryKeyJoinColumn(name = "student_checking_id")
public class StudentChecking extends Account {
    private String secretKey;

    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentChecking() {

    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.secretKey = secretKey;
        this.status = status;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, Date creationDate, String secretKey, Status status) {
        super(balance, primaryOwner, creationDate);
        this.secretKey = secretKey;
        this.status = status;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
