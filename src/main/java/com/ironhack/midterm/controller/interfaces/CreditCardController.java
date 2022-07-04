package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.CreditCard;

public interface CreditCardController {
    void updateBalance(long accountId, BalanceDTO balanceDTO);

    CreditCard store(CreditCard creditCard);

    void delete(long accountId);

    Money getBalanceBySecretKey(String secretKey);

    void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney);

}
