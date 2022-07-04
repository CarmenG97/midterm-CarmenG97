package com.ironhack.midterm.model;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "saving_id")
public class Saving extends Account {

    @Max(0)
    @DecimalMax("50")
    private BigDecimal interestRate;

    @Max(1000)
    @Min(100)
    private BigDecimal minimumBalance;

    private String secretKey;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Saving() {

    }

    public Saving(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate, BigDecimal interestRate, BigDecimal minimumBalance, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.status = status;
    }

    // Default value of interest rate 0.0025
    public Saving(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate, BigDecimal minimumBalance, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.interestRate = new BigDecimal("0.0025");
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.status = status;
    }

    // Default value of minimum balance 1000
    public Saving(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.interestRate = new BigDecimal("0.0025");
        this.minimumBalance = new BigDecimal("1000");
        this.secretKey = secretKey;
        this.status = status;
    }


    //Optional secondary owner


    public Saving(Money balance, AccountHolder primaryOwner, Date creationDate, BigDecimal interestRate, BigDecimal minimumBalance, String secretKey, Status status) {
        super(balance, primaryOwner, creationDate);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.status = status;
    }

    // If any account drops below the minimumBalance, the penaltyFee should be deducted from the balance automatically
    public void checkMinBalance(){
        if(getBalance().getAmount().compareTo(minimumBalance) == -1){
            BigDecimal amount = getBalance().getAmount();
            amount = getBalance().getAmount().subtract(getPenaltyFee().getAmount());
            getBalance().getAmount().equals(amount);
            setBalance(getBalance());
        }
    }


    /////////////////////////////////////
    //                                 //
    //      UPDATE INTERESTS           //
    //                                 //
    /////////////////////////////////////

    // Last interest update date
    //The first time this is the creationDate. Then we actualize this date
    Date lastUpdateDate = getCreationDate();

    private void updateInterest(){

        // Today date
        Date todayDate= new Date(String.valueOf(LocalDate.now()));
        Date todayYear = new Date (todayDate.getYear());

        // Last update date
        Date lastUpdateYear = new Date (lastUpdateDate.getYear());


        // We check if this year the interest has been updated
        int yearComparison = lastUpdateYear.compareTo(todayYear);


        if(yearComparison == 1) { // Next year

            // We update the interest each year, one to one

            // The balance is increased by a percentage equal to the interest of the account
            BigDecimal amount = getBalance().getAmount().multiply(interestRate.divide(new BigDecimal("100")));
            Money moneyAmount = new Money(amount, Currency.getInstance("EUR"));

            // New value of the balance
            BigDecimal newBalance = getBalance().increaseAmount(moneyAmount);
            Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));

            // Save it in the getBalance
            setBalance(newBalanceMoney);
        }

        // I change the last update date to today
        lastUpdateDate = todayDate;
    }



    public Money getBalance() {
        return super.getBalance();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
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
