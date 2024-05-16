package com.grupo5.SpringJpaToken.Dto;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicInsert
@DynamicUpdate
public class FireBaseAuth {
    private String nombre;
    private String username;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean status;
    private String UId;
    private String idToken;
    private String password;
}
