package com.grupo5.SpringJpaToken.Dto;

import com.grupo5.SpringJpaToken.Types.TypePrevia;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicInsert
@DynamicUpdate
public class PreviaDataDireccion {
    private Long from;
    private Long to;
    private TypePrevia requisito;

}
