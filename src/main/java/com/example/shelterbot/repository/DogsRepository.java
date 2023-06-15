package com.example.shelterbot.repository;
import com.example.shelterbot.model.Dogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DogsRepository extends JpaRepository<Dogs, Integer> {
}