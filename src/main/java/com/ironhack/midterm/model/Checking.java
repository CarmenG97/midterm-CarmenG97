package com.ironhack.midterm.model;


import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
@Entity
@PrimaryKeyJoinColumn(name = "checking_id")
public class Checking extends Account {


        //Constant values in the constructor (minimumBalance = 250, monthlyMaintencanceFee = 12)
        private BigDecimal minimumBalance;
        private int monthlyMaintenanceFee;

        private String secretKey;

        @Enumerated(EnumType.STRING)
        private Status status;

        public Checking() {

        }

        // Checking accounts have a default value of minumum balance of 250 and monthly maintenance fee of 12
        public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Date creationDate, String secretKey, Status status) {
                super(balance, primaryOwner, secondaryOwner, creationDate);
                this.minimumBalance = new BigDecimal("250");
                this.monthlyMaintenanceFee = 12;
                this.secretKey = secretKey;
                this.status = status;
        }


        // Optional secondary owner

    public Checking(Money balance, AccountHolder primaryOwner, Date creationDate, BigDecimal minimumBalance, int monthlyMaintenanceFee, String secretKey, Status status) {
        super(balance, primaryOwner, creationDate);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.secretKey = secretKey;
        this.status = status;
    }

    // If any account drops below the minimumBalance, the penaltyFee should be deducted from the balance automatically
       public void checkMinBalance(Money balance){
               if(balance.getAmount().compareTo(minimumBalance) == -1){
                   BigDecimal amount = balance.getAmount();
                   amount = balance.getAmount().subtract(getPenaltyFee().getAmount());
                   balance.getAmount().equals(amount);
                       setBalance(balance);
               }
       }

        public BigDecimal getMinimumBalance() {

                return minimumBalance;
        }

        public void setMinimumBalance(BigDecimal minimumBalance) {

                this.minimumBalance = minimumBalance;
        }

        public int getMonthlyMaintenanceFee() {

                return monthlyMaintenanceFee;
        }

        public void setMonthlyMaintenanceFee(int monthlyMaintenanceFee) {
                this.monthlyMaintenanceFee = monthlyMaintenanceFee;
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
