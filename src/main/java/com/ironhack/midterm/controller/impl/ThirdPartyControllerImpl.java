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

    // ThirdParty can receive and send money to other accounts
    // They must provide the account secret key. So, they can only send or receive money from checkings

    @PatchMapping("thirdParty/{operation}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeBalanceByThirdParty(@PathVariable(name = "id") long accountId, @PathVariable String operation, @RequestHeader String hashKey, @RequestParam String secretKey, @RequestBody @Valid Money transferMoney) {
 //      if(operation == "increase") {
           thirdPartyService.increaseBalanceByThirdParty(accountId, operation, hashKey, secretKey, transferMoney);
//       } else {
 //          thirdPartyService.decreaseBalanceByThirdParty(accountId, hashKey, secretKey, transferMoney);
 //      }
        //FALTA EL DECREASE MONEY, CUANDO EL THIRDPARTY COBRA AL USUARIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
    }


}
