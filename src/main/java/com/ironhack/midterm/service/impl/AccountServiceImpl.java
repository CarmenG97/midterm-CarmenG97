package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.model.Account;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    //Create a route to update the balance of the account
    public void updateBalance(long accountId, Money balance) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "account not found :c");
        }

        optionalAccount.get().setBalance(balance);
        accountRepository.save(optionalAccount.get());
    }


}
