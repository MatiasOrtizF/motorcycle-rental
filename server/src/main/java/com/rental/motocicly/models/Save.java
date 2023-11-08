package com.rental.motocicly.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "save")
public class Save {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "id_user")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User user;

    @JoinColumn(name = "id_motorcycle")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Motorcycle motorcycle;
}
