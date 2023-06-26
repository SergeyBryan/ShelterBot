package com.example.shelterbot.repository;
import com.example.shelterbot.model.Cats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatsRepository extends JpaRepository<Cats, Long> {

}