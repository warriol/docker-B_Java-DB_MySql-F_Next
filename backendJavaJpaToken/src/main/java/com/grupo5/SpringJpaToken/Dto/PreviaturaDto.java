package com.grupo5.SpringJpaToken.Dto;

import com.grupo5.SpringJpaToken.Types.TypePrevia;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicInsert
@DynamicUpdate
public class PreviaturaDto {
    private Long idAsigOrigen;
    private Long idPrevAsignada;
    private String nombrePrev;
    private TypePrevia requisito;
}
