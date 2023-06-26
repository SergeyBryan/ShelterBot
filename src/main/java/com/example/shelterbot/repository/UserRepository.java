package com.example.shelterbot.repository;
import com.example.shelterbot.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.trialPeriod = :newTrialPeriod WHERE u.id = :id")
    void extendTrialPeriod(LocalDateTime newTrialPeriod, String id);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.dog = case when :animal = 'DOG' then :petId else u.dog end" +
            ", u.cat = case when :animal = 'CAT' then :petId else u.cat end WHERE u.id = :id")
//    @Query("UPDATE User u set u.petID = :petId, u.animal = :animal WHERE u.id = :id")
    void addPetToOwner(@Param("petId") long petId,
                       @Param("animal") String animal,
                       @Param("id")String id);

    @Transactional
    User getUserByChatId(String chatId);
}