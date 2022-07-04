package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.classes.Money;

public interface AccountService {
    void updateBalance(long checkingId, Money balance);
}
