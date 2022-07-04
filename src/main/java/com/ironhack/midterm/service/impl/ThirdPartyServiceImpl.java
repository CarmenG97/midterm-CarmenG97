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

    // Thirdparty can add or remove money from accounts (regardless of type)

                    //*************************************************

                                     //DEPOSIT MONEY

                    //************************************************

    public void increaseBalanceByThirdParty(long accountId, String hashKey, String secretKey, Money transferMoney) {

        //The account who suffers the change in the balance
        Optional<Checking> optionalReceivingChecking = checkingRepository.findById(accountId);
        if (!optionalReceivingChecking.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :C");
        }

        if (optionalReceivingChecking.get().getSecretKey().equals(secretKey)) {

            BigDecimal newBalance;


                newBalance = optionalReceivingChecking.get().getBalance().increaseAmount(transferMoney);
                Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));

                // Throw exception if money is negative or 0
                if (newBalanceMoney.getAmount().compareTo(new BigDecimal("0")) == -1) {
                    throw new IllegalArgumentException("Money can not be less than 0 Euros");
                }

                optionalReceivingChecking.get().setBalance(newBalanceMoney);
                checkingRepository.save(optionalReceivingChecking.get()); // I save the receiving account with the modified balance

            }


        }

                     //*************************************************

                                     //REMOVE MONEY

                      //************************************************


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

}
