package com.example.shelterbot.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Cats extends Pet{

    public Cats(Long id, String name, int age, Shelter shelter) {
        super(id, name, age, shelter);
    }
}
