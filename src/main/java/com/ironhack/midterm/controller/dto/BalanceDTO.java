package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.classes.Money;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import java.math.BigDecimal;

public class BalanceDTO {

    private BigDecimal amount;
    private String currency;

    public BalanceDTO() {
    }

    public BalanceDTO(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
