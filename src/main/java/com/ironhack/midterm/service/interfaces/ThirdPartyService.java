package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;

import javax.websocket.Endpoint;

public interface ThirdPartyService {
    void increaseBalanceByThirdParty(long accountId, String operation, String hashKey, String secretKey, Money transferMoney);
//    void decreaseBalanceByThirdParty(long accountId, String hashKey, String operation, String secretKey, Money transferMoney);

}
