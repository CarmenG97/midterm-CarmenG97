package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.DateOfBirthDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.controller.interfaces.CheckingController;
import com.ironhack.midterm.model.Account;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.StudentChecking;
import com.ironhack.midterm.repository.StudentCheckingRepository;
import com.ironhack.midterm.service.interfaces.CheckingService;
import com.ironhack.midterm.service.interfaces.StudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Date;
import java.util.Optional;

@Service
public class StudentCheckingServiceImpl implements StudentCheckingService {
    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    CheckingController checkingController;

    AccountHolder accountHolder;

//------------------------------------------------------------------
    Integer todayYear = LocalDate.now().getYear();
//    Integer birthYear = accountHolder.getDateOfBirth().getYear();

//    Integer age = todayYear - birthYear;

//------------------------------------------------------------------

    public StudentChecking store(StudentChecking studentChecking) {
        // Condición if para la edad!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        StudentChecking newStudentChecking = new StudentChecking(studentChecking.getBalance(), studentChecking.getPrimaryOwner(), studentChecking.getSecondaryOwner(), studentChecking.getCreationDate(), studentChecking.getSecretKey(), studentChecking.getStatus());
        return studentCheckingRepository.save(newStudentChecking);
    }


    // Create a route to update the balance with a secret key (account holders)
    public Money findBalanceBySecretKey(String secretKey) {
        Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findStudentCheckingBySecretKey(secretKey);
        Optional<Money> optionalMoney = Optional.ofNullable(optionalStudentChecking.get().getBalance());
        if (!optionalMoney.isPresent()) {
            // Lanzar error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Checking not found :C");
        }

        // Show a record
        return optionalMoney.get();
    }


    //Create a route to change the balance of another account with the id and the primary or secondary owner name
    public void changeBalanceByName(long accountId, PrimaryOwnerDTO primaryOwnerDTO, String secretKey, Money transferMoney) {

        //The account who suffers the change in the balance
        Optional<StudentChecking> optionalReceivingAccount = studentCheckingRepository.findById(accountId);
        if(!optionalReceivingAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found :C");
        }


        BigDecimal newBalance = optionalReceivingAccount.get().getBalance().increaseAmount(transferMoney);
        Money newBalanceMoney = new Money(newBalance, Currency.getInstance("EUR"));

        optionalReceivingAccount.get().setBalance(newBalanceMoney);

        studentCheckingRepository.save(optionalReceivingAccount.get()); // I save the receiving account with the modified balance

        // Donate account
        Optional<StudentChecking> optionalDonorAccount = studentCheckingRepository.findStudentCheckingBySecretKey(secretKey);
        if (!optionalReceivingAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student checking not found :C");
        }

        BigDecimal newDonorBalance = optionalDonorAccount.get().getBalance().decreaseAmount(transferMoney);
        Money newDonorBalanceMoney = new Money(newDonorBalance, Currency.getInstance("EUR"));

        optionalDonorAccount.get().setBalance(newDonorBalanceMoney);

        studentCheckingRepository.save(optionalDonorAccount.get()); // I save the donor account with the modified balance
        //        }
    }


    // Create a route to delete an account
    public void delete(long accountId) {
        StudentChecking studentChecking = studentCheckingRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found :C"));
        studentCheckingRepository.delete(studentChecking);

    }




}
