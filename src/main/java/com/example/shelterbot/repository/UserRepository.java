package com.example.shelterbot.repository;
import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.trialPeriod = :newTrialPeriod WHERE u.id = :id")
    void extendTrialPeriod(LocalDateTime newTrialPeriod, String id);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.dog = :dog WHERE u.id = :id")
    void addDogToOwner(@Param("dog") Dogs dog,
                       @Param("id")long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.cat = :cat WHERE u.id = :id")
    void addCatToOwner(@Param("cat") Cats cat,
                       @Param("id")long id);

    @Transactional
    User getUserByChatId(long chatId);

    @Transactional
    List<User> getAllByCatIsNotNullAndDogIsNotNull();
}