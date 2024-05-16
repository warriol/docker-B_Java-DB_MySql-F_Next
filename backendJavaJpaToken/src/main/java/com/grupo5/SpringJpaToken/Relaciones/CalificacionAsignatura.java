package com.grupo5.SpringJpaToken.Relaciones;

import com.grupo5.SpringJpaToken.Types.TypeCalificacion;
import com.grupo5.SpringJpaToken.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "calificacion_Asinatura")
public class CalificacionAsignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeCalificacion resultado;

    // Relación muchos a uno con InscripcionExamen
    @ManyToOne
    @JoinColumn(name = "inscripcion_examen_id")
    private InscripcionAsignatura inscripcionAsignatura;

    // Relación muchos a uno con Usuario (Estudiante)
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Usuario estudiante;

    // Getters y Setters
}
