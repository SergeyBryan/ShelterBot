package com.example.shelterbot.repository;
import com.example.shelterbot.model.Volunteer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {

    @Transactional
    Volunteer getVolunteerByChatId(long chatId);
}