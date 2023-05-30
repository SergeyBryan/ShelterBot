package com.example.shelterbot.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Dog extends Pet{

    public Dog(Long id, String name, int age, Shelter shelter) {
        super(id, name, age, shelter);
    }
}
