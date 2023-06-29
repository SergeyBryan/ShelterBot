package com.example.shelterbot.repository;

import com.example.shelterbot.model.Report;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Report, Long> {

    @Transactional
    Report getByUserOwnerId(Long userOwner_id);

    @Transactional
    List<Report> getAllByUserOwnerId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Report r set r.petPhoto = :pathToPhoto where r.id = :id")
    void updatePhoto(String pathToPhoto, long id);
}
