package com.example.shelterbot.repository;

import com.example.shelterbot.model.DogsShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogsShelterRepository extends JpaRepository<DogsShelter, Integer> {
}
