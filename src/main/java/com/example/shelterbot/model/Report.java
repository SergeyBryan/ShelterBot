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

    @OneToOne
    @JoinColumn(name = "cat_id")
    private Cats catId;
    @OneToOne
    @JoinColumn(name = "dog_id")
    private Dogs dogId;

    @Column(name = "is_checked")
    private boolean isChecked;

    public Report() {
    }

    public Report(String petPhoto, String text, User userOwner, Cats cat, Dogs dog) {
        this.petPhoto = petPhoto;
        this.text = text;
        this.userOwner = userOwner;
        this.catId = cat;
        this.dogId = dog;
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

    public Cats getCatId() {
        return catId;
    }

    public void setCatId(Cats catId) {
        this.catId = catId;
    }

    public Dogs getDogId() {
        return dogId;
    }

    public void setDogId(Dogs dogId) {
        this.dogId = dogId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", createdTime=" + createdTime +
                ", petPhoto='" + petPhoto + '\'' +
                ", text='" + text + '\'' +
                ", userOwner=" + userOwner +
                ", catId=" + catId +
                ", dogId=" + dogId +
                '}';
    }
}
