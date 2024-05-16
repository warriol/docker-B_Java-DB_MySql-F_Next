package com.grupo5.SpringJpaToken.Dto;

import com.grupo5.SpringJpaToken.Relaciones.Previatura;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;
@Data
@DynamicInsert
@DynamicUpdate
public class AsignaturaDto {
    private Long id;
    private String nombre;
    private Long idCarrera;
    private List<PreviaturaDto> previaturas;

}
