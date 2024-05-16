package com.grupo5.SpringJpaToken.Dto;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicInsert
@DynamicUpdate
public class PreviasDataAsignatura {
    private Long id;
    private String label;
}
