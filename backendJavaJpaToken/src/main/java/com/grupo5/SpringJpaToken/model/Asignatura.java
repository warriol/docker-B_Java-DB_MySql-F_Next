package com.grupo5.SpringJpaToken.model;

import com.grupo5.SpringJpaToken.Relaciones.InscripcionAsignatura;
import com.grupo5.SpringJpaToken.Relaciones.Previatura;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "asignatura")
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Relación muchos a uno con Carrera
    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    // Relación uno a muchos con Previatura
    @OneToMany(mappedBy = "asignaturaOrigen")
    private List<Previatura> previaturas;

    // Relación uno a muchos con InscripcionAsignatura
    @OneToMany(mappedBy = "asignatura")
    private List<InscripcionAsignatura> inscripcionesAsignatura;

    // Relación uno a muchos con Examen
    @OneToMany(mappedBy = "asignatura")
    private List<Examen> examenes;
}
