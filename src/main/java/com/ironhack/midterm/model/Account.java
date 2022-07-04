package com.ironhack.midterm.model;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Date;
import java.util.Optional;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "`account`")
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency_balance")),
            @AttributeOverride(name = "rounding", column = @Column(name = "rounding_balance"))
    })
    private Money balance;

    // Each account has an accountHolder as primaryOwner and another as secondaryOwner
    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;  //Cambiado

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;  //Cambiado

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount_penalty_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency_penalty_fee")),
            @AttributeOverride(name = "rounding", column = @Column(name = "rounding_penalty_fee"))
    })

    private Money penaltyFee;

    private Date creationDate;


    public Account() {
    }

    // Penalty fee should be 40. So, we don't have to give it a value
    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = new Money(new BigDecimal("40"), Currency.getInstance("EUR"));
        this.creationDate = creationDate;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {

        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {

        this.secondaryOwner = secondaryOwner;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
