package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    @Query("SELECT c FROM CreditCard c WHERE secretKey = :secretKeyLike")
    Optional<CreditCard> findCreditCardBySecretKey(@Param("secretKeyLike") String secretKey);
}
