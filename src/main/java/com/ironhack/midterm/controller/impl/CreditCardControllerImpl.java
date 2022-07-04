package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.repository.CreditCardRepository;
import com.ironhack.midterm.service.interfaces.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CreditCardControllerImpl {

    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    CreditCardService creditCardService;


}
