package com.ironhack.midterm.model;

import com.ironhack.midterm.classes.Money;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "credit_card_id")
public class CreditCard extends Account {

    @Max(100000)
    @Min(100)
    private BigDecimal creditLimit;

    @Max(0)
    @DecimalMax("20")
    @Min(0)
    @DecimalMin("10")
    private BigDecimal interestRate;

    public CreditCard() {

    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    // Default credit limit of 100
    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.creditLimit = new BigDecimal(100);
        this.interestRate = interestRate;
    }

    // Default interest rate of 0.2
    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.creditLimit = new BigDecimal(100);
        this.interestRate = new BigDecimal(0.2);
    }

    /*
    //optional secondary owner
    public CreditCard(Money balance, AccountHolder primaryOwner, Money penaltyFee, String creationDate, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, penaltyFee, creationDate);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }
*/

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
        Date todayMonth = new Date (todayDate.getMonth());

        // Last update date
        Date lastUpdateYear = new Date (lastUpdateDate.getYear());
        Date lastUpdateMonth = new Date (lastUpdateDate.getMonth());


        // We check if this year the interest has been updated
        int yearComparison = lastUpdateYear.compareTo(todayYear);

        // We check if this month the interest has been updated
        int monthComparison = lastUpdateMonth.compareTo(todayMonth);


        if((monthComparison == 1) && (yearComparison == 0)) { // Next month in the same year
            // We update the interest each month, one to one

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



    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        updateInterest();
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
