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

    @Transactional
    @Modifying
    @Query("UPDATE Report r set r.text = :text where r.id = :reportID")
    void updateText(String text, Long reportID);

    @Transactional
    @Modifying
    @Query("UPDATE Report r set r.isChecked = true where r.id = :reportID")
    void updateIsChecked(long reportID);

    @Transactional
    @Query("SELECT r FROM Report r JOIN r.userOwner u WHERE u.chatId = :chatID AND date_trunc('day', r.createdTime )  = CURRENT_DATE")
    Report getToDayReportByUserChatId(Long chatID);

    @Transactional
    @Query("SELECT r FROM Report r WHERE date_trunc('day', r.createdTime )  = CURRENT_DATE")
    List<Report> getAllToDayReport();

    @Transactional
    List<Report> getAllByIsCheckedIsFalse();
}
