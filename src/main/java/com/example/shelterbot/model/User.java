package com.example.shelterbot.model;

import com.example.shelterbot.model.enums.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String chatId;
    private String phoneNum;
    private String address;
    private LocalDateTime trialPeriod;
    @OneToOne
    private Cats cat;
    @OneToOne
    private Dogs dog;

}
