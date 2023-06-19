package com.example.shelterbot.repository;

import com.example.shelterbot.model.Report;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Report, Long> {

    @Transactional
    Report getByUserOwner_Id(Long userOwner_id);
}
