package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.controller.interfaces.CheckingController;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.service.interfaces.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CheckingControllerImpl implements CheckingController {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    CheckingService checkingService;


                          /*#############

                              ADMINS

                          #############*/

    //Admins should be able to access to all accounts
    @GetMapping("/checkings")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAll() {
        return checkingRepository.findAll();
    }

    // Admins can create new accounts
    @PostMapping("/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking store(@RequestBody @Valid Checking checking) {

        return checkingRepository.save(checking);
    }


    // Admins should be able to access the balance for any account and to modify it
    @PatchMapping("/checking/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable(name = "id") long accountId, @RequestBody @Valid BalanceDTO checkingBalanceDTO) {
        checkingService.updateBalance(accountId, checkingBalanceDTO);
    }


                /*#########################

                     ACCOUNT HOLDERS

                 ###########################*/


    // AccountHolders should be able to access their own account balance
    // They can do it introducing their secret key in the body

    @GetMapping("/checking/{secretKey}")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalanceBySecretKey(@PathVariable(name = "secretKey") String secretKey) {
        return checkingService.findBalanceBySecretKey(secretKey);
    }


    // Account holders should be able to transfer money from any of their accounts to any other account (regardless of owner)
    // They can do it introducing the id of the account that should receive the transfer and the name of the primary or secondary account holder
    @PatchMapping("/checking/{id}/{primaryOwner}") // The id of the account in which you deposit the money
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeBalanceByName(@PathVariable(name = "id") long accountId, @PathVariable(name = "primaryOwner") PrimaryOwnerDTO primaryOwnerDTO, @RequestParam String secretKey, @RequestBody @Valid Money transferMoney) {
        checkingService.changeBalanceByName(accountId, primaryOwnerDTO, secretKey, transferMoney);
    }


    //Delete an account
    @DeleteMapping("/checking/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") long accountId) {
        checkingService.delete(accountId);
    }



}
