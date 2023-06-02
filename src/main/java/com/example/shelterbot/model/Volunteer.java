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
    String name;
    String chatId;
    String phoneNum;
}
