package com.ironhack.midterm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midterm.classes.Address;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "account_holder_id")
public class AccountHolder extends User {
    private String name;
    private Date dateOfBirth;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
    })

    private Address primaryAddress;
    private String mailingAddress;

    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> accountPrimaryList;

    @OneToMany(mappedBy = "secondaryOwner")
    @JsonIgnore
    private List<Account> accountSecondaryList;

    public AccountHolder() {
    }

    public AccountHolder(String name, Date dateOfBirth, Address primaryAddress, String mailingAddress) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<Account> getAccountPrimaryList() {
        return accountPrimaryList;
    }

    public void setAccountPrimaryList(List<Account> accountPrimaryList) {
        this.accountPrimaryList = accountPrimaryList;
    }

    public List<Account> getAccountSecondaryList() {
        return accountSecondaryList;
    }

    public void setAccountSecondaryList(List<Account> accountSecondaryList) {
        this.accountSecondaryList = accountSecondaryList;
    }
}
