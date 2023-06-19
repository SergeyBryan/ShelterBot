package com.example.shelterbot.repository;
import com.example.shelterbot.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.trialPeriod = :newTrialPeriod WHERE u.id = :id")
    void extendTrialPeriod(LocalDateTime newTrialPeriod, String id);
}