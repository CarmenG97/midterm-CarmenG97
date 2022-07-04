package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;

public interface ThirdPartyController {
    void increaseBalanceByThirdParty(long accountId, String hashKey, String secretKey, Money transferMoney);
    void decreaseBalanceByThirdParty(long accountId, String hashKey, String secretKey, Money transferMoney);

}
