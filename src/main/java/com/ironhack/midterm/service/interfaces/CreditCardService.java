package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Account;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.CreditCard;

public interface CreditCardService {
    void updateBalance(long creditCardId, Money balance);

    Money findBalanceBySecretKey(String secretKey);

    CreditCard store(CreditCard creditCard);

    void delete(long accountId);

    void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney);

}
