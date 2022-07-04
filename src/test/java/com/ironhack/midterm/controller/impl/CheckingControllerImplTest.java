package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.classes.Address;
import com.ironhack.midterm.classes.Money;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.PrimaryOwnerDTO;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Role;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.CheckingRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CheckingControllerImplTest {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc; // Simular peticiones HTTP
    private final ObjectMapper objectMapper = new ObjectMapper(); // Contruir Objetos JSON a partir de clase de JAVA

    private Checking checking1, checking2;

    //Create two different users and roles
    private User accountHolder, admin;
    private Role accountHolderRole, adminRole;


    @BeforeEach
    void setUp() {
        // Create the users and their roles
        accountHolder = new User("accountHolder", passwordEncoder.encode("123456"));
        accountHolderRole = new Role("ACCOUNT_HOLDER", accountHolder);
        accountHolder.setRoles(Set.of(accountHolderRole));

        admin = new User("admin", passwordEncoder.encode("123456"));
        adminRole = new Role("ADMIN", admin);
        admin.setRoles(Set.of(adminRole));

        userRepository.saveAll(List.of(accountHolder, admin)); // Save the data

        Money balance1 = new Money(new BigDecimal("3000"), Currency.getInstance("EUR"));
        Address address1 = new Address("C/Granada", "Algeciras", 11201);
        Address address2 = new Address("C/Cadiz", "Almeria", 18001);

        AccountHolder primaryOwner1 = new AccountHolder("Carmen", new Date(107, 4, 4), address1, "caarmengomezh@hotmail.com");
        AccountHolder secondaryOwner1 = new AccountHolder("Juanfran", new Date(105, 10, 11), address2, "juanfran@hotmail.com");

        accountHolderRepository.saveAll(List.of(primaryOwner1, secondaryOwner1));


        Address address21 = new Address("C/Asturias", "Valencia", 13005);
        Address address22 = new Address("C/Malaga", "Granada", 18003);

        AccountHolder primaryOwner2 = new AccountHolder("Palma", new Date(109, 9, 21), address1, "palma@hotmail.com");
        AccountHolder secondaryOwner2 = new AccountHolder("Angel", new Date(106, 8, 2), address2, "angel@hotmail.com");

        accountHolderRepository.saveAll(List.of(primaryOwner2, secondaryOwner2));


        checking1 = new Checking(balance1, primaryOwner1, secondaryOwner1, new Date(116, 5,3), "1244", Status.ACTIVE);
        checking2 = new Checking(balance1, primaryOwner2, secondaryOwner2, new Date(122, 4,12), "756", Status.ACTIVE);

        checkingRepository.saveAll(List.of(checking1, checking2));

    }

    @AfterEach
    void tearDown() {

        checkingRepository.deleteAll();
        userRepository.deleteAll();
    }

                              /*#############

                                  ADMINS

                             #############*/

    @Test
    void findAll_NoParams_AllCourses() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWRtaW46MTIzNDU2");

        MvcResult mvcResult = mockMvc.perform(
                get("/checkings")
                        .headers(httpHeaders)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains(checking1.getBalance().getAmount().toString()));
        assertTrue(mvcResult.getResponse().getContentAsString().contains(checking2.getSecretKey()));
    }

    @Test
    void updateBalance_Balance_NewAccount() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWRtaW46MTIzNDU2");

        Money newBalance = new Money(new BigDecimal("2510"), Currency.getInstance("EUR"));

        BalanceDTO balanceDTO = new BalanceDTO(new BigDecimal("2510"), "EUR");

        String body = objectMapper.writeValueAsString(balanceDTO);

        MvcResult mvcResult = mockMvc.perform(
                        patch("/checking/" + checking1.getAccountId() + "/balance")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(httpHeaders)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        Optional<Checking> optionalChecking = checkingRepository.findById(checking1.getAccountId());
        assertTrue(optionalChecking.isPresent());
        assertEquals(newBalance.getAmount(), optionalChecking.get().getBalance().getAmount());

    }


    @Test
    void store_NewAccount_NewAccount() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWRtaW46MTIzNDU2");

        Money balance = new Money(new BigDecimal("5050"), Currency.getInstance("EUR"));
        Address address_pepa = new Address("C/Granada", "Sevilla", 11201);
        Address address_pepe = new Address("C/Cadiz", "Cordona", 18001);
        AccountHolder primaryOwner_pepa = new AccountHolder("Pepa", new Date(111, 2, 4), address_pepa, "pepa@hotmail.com");
        AccountHolder secondaryOwner_pepe = new AccountHolder("Pepe", new Date(110, 8, 15), address_pepe, "pepe@hotmail.com");
        accountHolderRepository.saveAll(List.of(primaryOwner_pepa, secondaryOwner_pepe));

        Checking newChecking = new Checking(balance, primaryOwner_pepa, secondaryOwner_pepe, new Date(119, 11,24), "593", Status.FROZEN);

        String body = objectMapper.writeValueAsString(newChecking);

        MvcResult mvcResult = mockMvc.perform(
                        post("/checking")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(httpHeaders)
                )
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains(balance.getAmount().toString()));

    }

                  /*#########################

                     ACCOUNT HOLDERS

                 ###########################*/

    @Test
    void getBalanceBySecretKey_SecretKey_Balance() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWNjb3VudEhvbGRlcjoxMjM0NTY=");

        MvcResult mvcResult = mockMvc.perform(
                        get("/checking/" + checking1.getSecretKey())
                                .param("secretKey", "1244")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(httpHeaders)
                )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("3000"));

    }

   @Test
    void changeBalanceByName_AccountName_NewBalance() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWNjb3VudEhvbGRlcjoxMjM0NTY=");

        Money money = new Money(new BigDecimal("50"), Currency.getInstance("EUR"));

        BigDecimal newBalance = checking1.getBalance().increaseAmount(money);

        PrimaryOwnerDTO primaryOwnerDTO = new PrimaryOwnerDTO("Carmen"); // Receiving person

        String body = objectMapper.writeValueAsString(money);

        MvcResult mvcResult = mockMvc.perform(
                        patch("/checking/" + checking1.getAccountId() + "/" + primaryOwnerDTO)
                                .content(body)
                                .param("secretKey", "756")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(httpHeaders)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        Optional<Checking> optionalChecking = checkingRepository.findById(checking1.getAccountId());
        assertTrue(optionalChecking.isPresent());
        assertEquals(newBalance, optionalChecking.get().getBalance().getAmount());


}

    @Test
    void delete_validId_CheckingRemoved() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWNjb3VudEhvbGRlcjoxMjM0NTY=");

        MvcResult mvcResult = mockMvc.perform(
                delete("/checking/" + checking1.getAccountId())
                        .headers(httpHeaders)
                )
                .andExpect(status().isNoContent())
                .andReturn();
        assertFalse(checkingRepository.existsById(checking1.getAccountId()));
    }

}