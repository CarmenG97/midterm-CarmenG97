package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.service.interfaces.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderServiceImpl implements AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;
/*
    DateOfBirthDTO dateOfBirthDTO;
    //Student Checking if the age of primaryHolderAccount is < 24

    Integer todayYear = LocalDate.now().getYear();
    Integer birthYear = dateOfBirthDTO.getDateOfBirth().getYear();

//    Integer ageAccountHolder = todayYear - birthYear;

    /*
    if(ageAccountHolder < 24) {

    }
    */

}
