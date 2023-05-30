package com.example.shelterbot.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@MappedSuperclass
@NoArgsConstructor
public abstract class Shelter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @OneToMany(
            mappedBy = "pet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Pet> pet;

    public Shelter(Long id, String name, int age, List<Pet> pet) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.pet = pet;
    }

}
