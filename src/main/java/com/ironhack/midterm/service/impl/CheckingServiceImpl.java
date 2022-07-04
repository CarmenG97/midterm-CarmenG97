package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Account;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.service.interfaces.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

import java.util.Currency;
import java.util.Optional;
@Service
public class CheckingServiceImpl implements CheckingService {
    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountRepository accountRepository;


    //Create a route to update the balance of the account (admins)
    public void updateBalance(long accountId, BalanceDTO balanceDTO) {
        Money balanceMoney = new Money(balanceDTO.getAmount(), Currency.getInstance(balanceDTO.getCurrency()));
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :c");
        }

        optionalAccount.get().setBalance(balanceMoney);

        accountRepository.save(optionalAccount.get());
    }


    // Create a route to create a new account (admins)

    public Checking store(Checking checking) {
        // Condición if para la edad
        Checking newChecking = new Checking(checking.getBalance(), checking.getPrimaryOwner(), checking.getSecondaryOwner(), checking.getCreationDate(), checking.getSecretKey(), checking.getStatus());
        return checkingRepository.save(newChecking);
    }


    // Create a route to update the balance with a secret key (account holders)
    public Money findBalanceBySecretKey(String secretKey) {
        Optional<Checking> optionalChecking = checkingRepository.findCheckingBySecretKey(secretKey);
        Optional<Money> optionalMoney = Optional.ofNullable(optionalChecking.get().getBalance());
        if (!optionalMoney.isPresent()) {
            // Lanzar error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found :C");
        }

        // Show a record
        return optionalMoney.get();
    }


    //Create a route to change the balance of another account with the id and the primary or secondary owner name
    public void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney) {

 //       Money transferMoney = new Money(balanceDTO.getAmount(), Currency.getInstance(balanceDTO.getCurrency()));

        //The account who suffers the change in the balance
        Optional<Checking> optionalReceivingAccount = checkingRepository.findById(accountId);
        if(!optionalReceivingAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found :C");
        }

        //if(optionalReceivingAccount.get().getPrimaryOwner().equals(primaryOwnerDTO)) {

            BigDecimal newBalance = optionalReceivingAccount.get().getBalance().increaseAmount(transferMoney);
            Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));

            optionalReceivingAccount.get().setBalance(newBalanceMoney);

            checkingRepository.save(optionalReceivingAccount.get()); // I save the receiving account with the modified balance

            // Donate account
            Optional<Checking> optionalDonorAccount = checkingRepository.findCheckingBySecretKey(secretKey);
            if (!optionalReceivingAccount.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found :C");
            }

            BigDecimal newDonorBalance = optionalDonorAccount.get().getBalance().decreaseAmount(transferMoney);
            Money newDonorBalanceMoney = new Money(newDonorBalance, Currency.getInstance("EUR"));

            optionalDonorAccount.get().setBalance(newDonorBalanceMoney);

            checkingRepository.save(optionalDonorAccount.get()); // I save the donor account with the modified balance
         //        }
    }



    // Create a route to delete an account
    public void delete(long accountId) {
        Checking checking = checkingRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :C"));
        checkingRepository.delete(checking);

    }



}
