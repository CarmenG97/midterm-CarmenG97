package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.service.interfaces.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.Endpoint;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private CheckingRepository checkingRepository;


    // Thirdparty deposits money into the account

    public void increaseBalanceByThirdParty(long accountId, String operation, String hashKey, String secretKey, Money transferMoney) {

        //The account who suffers the change in the balance
        Optional<Checking> optionalReceivingChecking = checkingRepository.findById(accountId);
        if(!optionalReceivingChecking.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :C");
        }

        if(optionalReceivingChecking.get().getSecretKey().equals(secretKey)) {

            BigDecimal newBalance;

            if(operation == "increase") {

                newBalance = optionalReceivingChecking.get().getBalance().increaseAmount(transferMoney);
                Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));
                optionalReceivingChecking.get().setBalance(newBalanceMoney);
                checkingRepository.save(optionalReceivingChecking.get()); // I save the receiving account with the modified balance

            } else if (operation == "decrease"){

                newBalance = optionalReceivingChecking.get().getBalance().decreaseAmount(transferMoney);
                Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));
                optionalReceivingChecking.get().setBalance(newBalanceMoney);
                checkingRepository.save(optionalReceivingChecking.get()); // I save the receiving account with the modified balance

            }

 //           Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));

//            optionalReceivingChecking.get().setBalance(newBalanceMoney);

//            checkingRepository.save(optionalReceivingChecking.get()); // I save the receiving account with the modified balance

        }

    }

    /*
    // Thirdparty collects money from the account

    public void decreaseBalanceByThirdParty(long accountId, String hashKey, String secretKey, Money transferMoney) {

        //The account who suffers the change in the balance
        Optional<Checking> optionalReceivingChecking = checkingRepository.findById(accountId);
        if(!optionalReceivingChecking.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :C");
        }

        if(optionalReceivingChecking.get().getSecretKey().equals(secretKey)) {

            BigDecimal newBalance = optionalReceivingChecking.get().getBalance().decreaseAmount(transferMoney);
            Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));

            optionalReceivingChecking.get().setBalance(newBalanceMoney);

            checkingRepository.save(optionalReceivingChecking.get()); // I save the receiving account with the modified balance

        }

    }
*/

}
