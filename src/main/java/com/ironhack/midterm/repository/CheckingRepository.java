package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {

    // This is a JPQL query
    @Query("SELECT c FROM Checking c WHERE secretKey = :secretKeyLike")
    Optional<Checking> findCheckingBySecretKey(@Param("secretKeyLike") String secretKey);


}
