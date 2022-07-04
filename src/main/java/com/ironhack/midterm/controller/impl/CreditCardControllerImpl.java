package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.repository.CreditCardRepository;
import com.ironhack.midterm.service.interfaces.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@RestController
public class CreditCardControllerImpl {

    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    CreditCardService creditCardService;


                          /*#############

                              ADMINS

                          #############*/

    //Admins should be able to access to all accounts
    @GetMapping("/creditcards")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    // Admins can create new accounts
    @PostMapping("/creditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard store(@RequestBody @Valid CreditCard creditCard) {

        return creditCardRepository.save(creditCard);
    }


    // Admins should be able to access the balance for any account and to modify it
    /*
    @PatchMapping("/creditcard/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable(name = "id") long accountId, @RequestBody @Valid BalanceDTO checkingBalanceDTO) {
        creditCardService.updateBalance(accountId, checkingBalanceDTO);
    }

    */

                /*#########################

                     ACCOUNT HOLDERS

                 ###########################*/





    // Account holders should be able to transfer money from any of their accounts to any other account (regardless of owner)
    // They can do it introducing the id of the account that should receive the transfer and the name of the primary or secondary account holder
    @PatchMapping("/creditcard/{id}/{primaryOwner}") // The id of the account in which you deposit the money
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeBalanceByName(@PathVariable(name = "id") long accountId, @PathVariable(name = "primaryOwner") PrimaryOwnerDTO primaryOwnerDTO, @RequestParam String secretKey, @RequestBody @Valid Money transferMoney) {
        creditCardService.changeBalanceByName(accountId, primaryOwnerDTO, secretKey, transferMoney);
    }


    //Delete an account
    @DeleteMapping("/creditcard/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") long accountId) {
        creditCardService.delete(accountId);
    }



}
