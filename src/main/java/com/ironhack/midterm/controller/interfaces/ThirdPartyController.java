package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;

public interface ThirdPartyController {
    void changeBalanceByThirdParty(long accountId, String operation, String hashKey, String secretKey, Money transferMoney);

}
