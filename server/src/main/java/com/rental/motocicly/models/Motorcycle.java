package com.rental.motocicly.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "motorcycle")
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "motorcycle_name")
    private String motorcycleName;

    @Column(name = "image")
    private String image;

    @Column(name = "gps")
    private Boolean gps;

    @Column(name = "price")
    private Integer Price;
}
