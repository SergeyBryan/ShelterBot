package com.example.shelterbot.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@MappedSuperclass
@NoArgsConstructor
public abstract class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public Pet(Long id, String name, int age, Shelter shelter) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.shelter = shelter;
    }
}
