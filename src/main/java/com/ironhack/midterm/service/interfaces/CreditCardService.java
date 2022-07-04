package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.classes.Money;

public interface CreditCardService {
    void updateBalance(long creditCardId, Money balance);
}
