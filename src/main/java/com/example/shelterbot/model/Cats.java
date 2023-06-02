package com.example.shelterbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "cats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cats  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "shelter")
    private CatsShelter shelter;
}
