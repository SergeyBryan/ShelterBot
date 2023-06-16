package com.example.shelterbot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "pet_photo")
    private String petPhoto;

    @Column(name = "diet")
    private String diet;

    @Column(name = "general_health")
    private String generalHealth;

    @Column(name = "behavior_change")
    private String behaviorChange;

    @OneToOne()
    private User userOwner;

    @Column(name = "pet_id")
    private Long PetId;

    public Report() {
    }

    public Report(String petPhoto, String diet, String generalHealth, String behaviorChange, User userOwner, Long petId) {
        this.petPhoto = petPhoto;
        this.diet = diet;
        this.generalHealth = generalHealth;
        this.behaviorChange = behaviorChange;
        this.userOwner = userOwner;
        PetId = petId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getPetPhoto() {
        return petPhoto;
    }

    public void setPetPhoto(String petPhoto) {
        this.petPhoto = petPhoto;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getGeneralHealth() {
        return generalHealth;
    }

    public void setGeneralHealth(String generalHealth) {
        this.generalHealth = generalHealth;
    }

    public String getBehaviorChange() {
        return behaviorChange;
    }

    public void setBehaviorChange(String behaviorChange) {
        this.behaviorChange = behaviorChange;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public Long getPetId() {
        return PetId;
    }

    public void setPetId(Long petId) {
        PetId = petId;
    }
}
