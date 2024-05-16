package com.grupo5.SpringJpaToken.Relaciones;

import com.grupo5.SpringJpaToken.Types.TypePrevia;
import com.grupo5.SpringJpaToken.model.Asignatura;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "previatura")
public class Previatura {//Se pueden generar repetidos, con distintos id, dilema cada asignatura puede ser que tenga distinto requisito de cada previa por eso se repetirian
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long previaturaID;

    private TypePrevia requisito;
    private Long idCarrera;

    private String nombrePrev; //Para Query que me permita saber que no estoy reasignado 2 veses la misma previa

    @ManyToOne
    @JoinColumn(name = "asignatura_id_origen")
    private Asignatura asignaturaOrigen;

    @ManyToOne
    @JoinColumn(name = "asignatura_id_previa")
    private Asignatura asignaturaPrevia;


}
