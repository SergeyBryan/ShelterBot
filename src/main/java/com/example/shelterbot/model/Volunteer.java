package com.example.shelterbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volunteer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long chatId;
    private String phoneNum;

    public Volunteer(String name, Long chatId, String phoneNum) {
        this.name = name;
        this.chatId = chatId;
        this.phoneNum = phoneNum;
    }
}
