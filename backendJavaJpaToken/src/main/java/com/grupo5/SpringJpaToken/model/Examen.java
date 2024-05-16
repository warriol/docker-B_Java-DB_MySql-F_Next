package com.grupo5.SpringJpaToken.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "examen")
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private int anioLectivo;
    private String semestre;

    // Relación muchos a uno con Asignatura
    @ManyToOne
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

    // Getters y Setters
}
