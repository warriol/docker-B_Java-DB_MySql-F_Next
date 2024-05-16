package com.grupo5.SpringJpaToken.Relaciones;

import com.grupo5.SpringJpaToken.model.Asignatura;
import com.grupo5.SpringJpaToken.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "inscripcion_asignatura")
public class InscripcionAsignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int anioLectivo;
    private String semestre;

    // Relación muchos a uno con Usuario (Estudiante)
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Usuario estudiante;

    // Relación muchos a uno con Asignatura
    @ManyToOne
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

}
