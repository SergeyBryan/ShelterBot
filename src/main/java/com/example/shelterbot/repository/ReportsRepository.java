package com.example.shelterbot.repository;

import com.example.shelterbot.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Report, Long> {
}
