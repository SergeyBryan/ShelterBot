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

    @Column(name = "text")
    private String text;

    @OneToOne()
    private User userOwner;

    @Column(name = "pet_id")
    private Long PetId;

    public Report() {
    }

    public Report(String petPhoto, String text, User userOwner, Long petId) {
        this.petPhoto = petPhoto;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
