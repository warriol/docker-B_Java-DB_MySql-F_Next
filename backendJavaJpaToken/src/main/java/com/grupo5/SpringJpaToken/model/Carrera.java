package com.grupo5.SpringJpaToken.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int duracion;

    // Relación uno a muchos con Asignatura
    @OneToMany(mappedBy = "carrera")
    private List<Asignatura> asignaturas;

}