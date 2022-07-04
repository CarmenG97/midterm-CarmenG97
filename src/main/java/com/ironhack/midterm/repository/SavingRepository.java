package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {
    // This is a JPQL query
    @Query("SELECT s FROM Saving s WHERE secretKey = :secretKeyLike")
    Optional<Saving> findSavingBySecretKey(@Param("secretKeyLike") String secretKey);
}
