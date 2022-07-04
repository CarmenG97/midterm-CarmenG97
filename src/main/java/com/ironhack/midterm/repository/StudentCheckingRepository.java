package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
    // This is a JPQL query
    @Query("SELECT s FROM StudentChecking s WHERE secretKey = :secretKeyLike")
    Optional<StudentChecking> findStudentCheckingBySecretKey(@Param("secretKeyLike") String secretKey);
}
