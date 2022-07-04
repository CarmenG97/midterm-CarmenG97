package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Account;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;

import java.lang.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CheckingController {

//    List<Checking> findAll();
//    Checking findById(long accountId);

    void updateBalance(long accountId, BalanceDTO balanceDTO);

    Checking store(Checking checking);

    void delete(long accountId);

    Money getBalanceBySecretKey(String secretKey);

    void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney);
}
