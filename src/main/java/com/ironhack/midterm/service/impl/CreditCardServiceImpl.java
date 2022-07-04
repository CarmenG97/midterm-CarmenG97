package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.CreditCardRepository;
import com.ironhack.midterm.service.interfaces.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;



    //Create a route to update the balance of the account
    public void updateBalance(long creditCardId, Money balance) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if(!optionalCreditCard.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "account not found :c");
        }

        optionalCreditCard.get().setBalance(balance);
        creditCardRepository.save(optionalCreditCard.get());
    }


}
