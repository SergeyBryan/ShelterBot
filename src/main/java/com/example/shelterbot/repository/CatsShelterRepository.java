package com.example.shelterbot.repository;
import com.example.shelterbot.model.CatsShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatsShelterRepository extends JpaRepository<CatsShelter, Integer> {

}