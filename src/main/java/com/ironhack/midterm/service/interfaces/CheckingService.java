package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Account;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;

import java.math.BigDecimal;
import java.util.Optional;

public interface CheckingService {
    void updateBalance(long accountId, BalanceDTO balanceDTO);

    Money findBalanceBySecretKey(String secretKey);

    Account store(Checking checking);

    void delete(long accountId);

    void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney);
}
