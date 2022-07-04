package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Saving;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.SavingRepository;
import com.ironhack.midterm.service.interfaces.CheckingService;
import com.ironhack.midterm.service.interfaces.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SavingControllerImpl {
    @Autowired
    SavingRepository savingRepository;

    @Autowired
    SavingService savingService;


                          /*#############

                              ADMINS

                          #############*/

    //Admins should be able to access to all accounts
    @GetMapping("/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findAll() {
        return savingRepository.findAll();
    }

    // Admins should be able to access the balance for any account and to modify it
    @PatchMapping("/saving/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable(name = "id") long accountId, @RequestBody @Valid BalanceDTO checkingBalanceDTO) {
        savingService.updateBalance(accountId, checkingBalanceDTO);
    }

    // Admins can create new accounts
    @PostMapping("/saving")
    @ResponseStatus(HttpStatus.CREATED)
    public Saving store(@RequestBody @Valid Saving saving) {

        return savingRepository.save(saving);
    }


                /*#########################

                     ACCOUNT HOLDERS

                 ###########################*/


    // AccountHolders should be able to access their own account balance
    // They can do it introducing their secret key in the body

    @GetMapping("/saving/{secretKey}")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalanceBySecretKey(@PathVariable(name = "secretKey") String secretKey) {
        return savingService.findBalanceBySecretKey(secretKey);
    }


    // Account holders should be able to transfer money from any of their accounts to any other account (regardless of owner)
    // They can do it introducing the id of the account that should receive the transfer and the name of the primary or secondary account holder
    @PatchMapping("/saving/{id}/{primaryOwner}") // The id of the account in which you deposit the money
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeBalanceByName(@PathVariable(name = "id") long accountId, @PathVariable(name = "primaryOwner") PrimaryOwnerDTO primaryOwnerDTO, @RequestParam String secretKey, @RequestBody @Valid Money transferMoney) {
        savingService.changeBalanceByName(accountId, primaryOwnerDTO, secretKey, transferMoney);
    }


    //Delete an account
    @DeleteMapping("/saving/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") long accountId) {
        savingService.delete(accountId);
    }


}
