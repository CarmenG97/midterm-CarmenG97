package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Account;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Saving;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.SavingRepository;
import com.ironhack.midterm.service.interfaces.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Service
public class SavingServiceImpl implements SavingService {
    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private AccountRepository accountRepository;

                              /*#############

                              ADMINS

                          #############*/

    // Create a route to create a new account (admins)

    public Saving store(Saving saving) {
        // Condición if para la edad
        Saving newSaving = new Saving(saving.getBalance(), saving.getPrimaryOwner(), saving.getSecondaryOwner(), saving.getCreationDate(), saving.getSecretKey(), saving.getStatus());
        return savingRepository.save(newSaving);
    }


    //Create a route to update the balance of the account (admins)
    public void updateBalance(long accountId, BalanceDTO balanceDTO) {
        Money balanceMoney = new Money(balanceDTO.getAmount(), Currency.getInstance(balanceDTO.getCurrency()));

        // Throw exception if money is negative or 0
        if(balanceMoney.getAmount().compareTo(new BigDecimal("0")) == -1){
            throw new IllegalArgumentException("Money can not be less than 0 Euros");
        }

        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :c");
        }

        optionalAccount.get().setBalance(balanceMoney);

        accountRepository.save(optionalAccount.get());
    }




              /*#########################

                    ACCOUNT HOLDERS

              ###########################*/


    // Create a route to update the balance with a secret key (account holders)
    public Money findBalanceBySecretKey(String secretKey) {
        Optional<Saving> optionalSaving = savingRepository.findSavingBySecretKey(secretKey);
        Optional<Money> optionalMoney = Optional.ofNullable(optionalSaving.get().getBalance());
        if (!optionalMoney.isPresent()) {
            // Lanzar error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found :C");
        }

        // Show a record
        return optionalMoney.get();
    }


    //Create a route to change the balance of another account with the id and the primary or secondary owner name
    public void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney) {

        //The account who suffers the change in the balance
        Optional<Saving> optionalReceivingAccount = savingRepository.findById(accountId);
        if(!optionalReceivingAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found :C");
        }


        BigDecimal newBalance = optionalReceivingAccount.get().getBalance().increaseAmount(transferMoney);
        Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));

        // Throw exception if money is negative or 0
        if(newBalanceMoney.getAmount().compareTo(new BigDecimal("0")) == -1){
            throw new IllegalArgumentException("Money can not be less than 0 Euros");
        }

        optionalReceivingAccount.get().setBalance(newBalanceMoney);

        savingRepository.save(optionalReceivingAccount.get()); // I save the receiving account with the modified balance

        // Donate account
        Optional<Saving> optionalDonorAccount = savingRepository.findSavingBySecretKey(secretKey);
        if (!optionalReceivingAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found :C");
        }

        BigDecimal newDonorBalance = optionalDonorAccount.get().getBalance().decreaseAmount(transferMoney);
        Money newDonorBalanceMoney = new Money(newDonorBalance, Currency.getInstance("EUR"));

        optionalDonorAccount.get().setBalance(newDonorBalanceMoney);

        savingRepository.save(optionalDonorAccount.get()); // I save the donor account with the modified balance

    }



    // Create a route to delete an account
    public void delete(long accountId) {
        Saving saving = savingRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :C"));
        savingRepository.delete(saving);

    }


}
