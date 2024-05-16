package com.grupo5.SpringJpaToken.Dto;

import com.grupo5.SpringJpaToken.model.Asignatura;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@DynamicInsert
@DynamicUpdate
public class CarreraDto {
    private Long id;
    private String nombre;
    private int duracion;
    private List<AsignaturaDto> asignaturas;
}
