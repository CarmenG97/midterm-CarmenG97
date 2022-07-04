package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.classes.Address;
import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ThirdPartyControllerImplTest {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingRepository checkingRepository;


    @Autowired
    private MockMvc mockMvc; // Simular peticiones HTTP
    private final ObjectMapper objectMapper = new ObjectMapper(); // Contruir Objetos JSON a partir de clase de JAVA

    private Checking checking;
    private ThirdParty thirdParty;


    @BeforeEach
    void setUp() {

        // Create a checking account
        Money balance = new Money(new BigDecimal("400"), Currency.getInstance("EUR"));
        Address address = new Address("C/Cerezo", "Almeria", 18002);
        AccountHolder primaryOwner = new AccountHolder("Cristina", new Date(112, 5, 12), address, "cristina@hotmail.com");
        accountHolderRepository.save(primaryOwner);

        checking = new Checking(balance, primaryOwner, primaryOwner, new Date(102, 6, 3), "1234", Status.ACTIVE);
        checkingRepository.save(checking);

        thirdParty = new ThirdParty("Claudia", "asad");
        thirdPartyRepository.save(thirdParty);

    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
        thirdPartyRepository.deleteAll();

    }

                         //*************************************************

                                           //DEPOSIT MONEY

                         //************************************************

    @Test
    void increaseBalanceByThirdParty_ValidParameters_NewBalance() throws Exception {

        //They do not need authorization
        Money money = new Money(new BigDecimal("100"), Currency.getInstance("EUR"));

        Money newBalance = new Money(checking.getBalance().increaseAmount(money), Currency.getInstance("EUR"));

        String body = objectMapper.writeValueAsString(money);


        MvcResult mvcResult = mockMvc.perform(
                        patch("/thirdParty/increase/" + checking.getAccountId())
                                .content(body)
                                .header("hashKey", "asad")
                                .param("secretKey", "1234")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        Optional<Checking> optionalChecking = checkingRepository.findById(checking.getAccountId());
        assertTrue(optionalChecking.isPresent());

        assertEquals(newBalance.getAmount(), optionalChecking.get().getBalance().getAmount());

    }

                         //*************************************************

                                            //REMOVE MONEY

                          //************************************************

    @Test
    void decreaseBalanceByThirdParty_ValidParameters_NewBalance() throws Exception {

        //They do not need authorization
        Money money = new Money(new BigDecimal("100"), Currency.getInstance("EUR"));

        Money newBalance = new Money(checking.getBalance().decreaseAmount(money), Currency.getInstance("EUR"));

        String body = objectMapper.writeValueAsString(money);


        MvcResult mvcResult = mockMvc.perform(
                        patch("/thirdParty/decrease/" + checking.getAccountId())
                                .content(body)
                                .header("hashKey", "asad")
                                .param("secretKey", "1234")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        Optional<Checking> optionalChecking = checkingRepository.findById(checking.getAccountId());
        assertTrue(optionalChecking.isPresent());

        assertEquals(newBalance.getAmount(), optionalChecking.get().getBalance().getAmount());

    }

}