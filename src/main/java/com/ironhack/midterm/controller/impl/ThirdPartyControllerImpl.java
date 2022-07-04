package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.controller.interfaces.ThirdPartyController;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.service.interfaces.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
public class ThirdPartyControllerImpl implements ThirdPartyController {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    ThirdPartyService thirdPartyService;

    // Thirdparty can add or remove money from accounts (regardless of type)
    // They must provide the account secret key. So, they can only send or receive money from checkings


                          //*************************************************

                                               //DEPOSIT MONEY

                        //************************************************
    @PatchMapping("thirdParty/increase/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void increaseBalanceByThirdParty(@PathVariable(name = "id") long accountId, @RequestHeader String hashKey, @RequestParam String secretKey, @RequestBody @Valid Money transferMoney) {

           thirdPartyService.increaseBalanceByThirdParty(accountId, hashKey, secretKey, transferMoney);

    }

                         //*************************************************

                                          //REMOVE MONEY

                         //************************************************

    @PatchMapping("thirdParty/decrease/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decreaseBalanceByThirdParty(@PathVariable(name = "id") long accountId, @RequestHeader String hashKey, @RequestParam String secretKey, @RequestBody @Valid Money transferMoney) {

        thirdPartyService.decreaseBalanceByThirdParty(accountId, hashKey, secretKey, transferMoney);

    }

}
