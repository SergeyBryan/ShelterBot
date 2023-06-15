package com.example.shelterbot.repository;
import com.example.shelterbot.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
}