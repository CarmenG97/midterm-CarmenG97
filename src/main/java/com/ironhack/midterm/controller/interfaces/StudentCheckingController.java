package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.BalanceDTO;

public interface StudentCheckingController {
    void updateBalance(long accountId, BalanceDTO balanceDTO);
}
