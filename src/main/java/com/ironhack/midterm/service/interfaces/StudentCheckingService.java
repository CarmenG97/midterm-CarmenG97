package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.StudentChecking;

public interface StudentCheckingService {
    StudentChecking store(StudentChecking studentChecking);

    void delete(long accountId);

    Money findBalanceBySecretKey(String secretKey);

    void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney);
}
