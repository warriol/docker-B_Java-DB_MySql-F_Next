package com.grupo5.SpringJpaToken.Relaciones;

import com.grupo5.SpringJpaToken.model.Examen;
import com.grupo5.SpringJpaToken.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "inscripcion_examen")
public class InscripcionExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inscripcionExamenID;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Usuario estudiante;

    @ManyToOne
    @JoinColumn(name = "examen_id")
    private Examen examen;

    private String resultado;
}