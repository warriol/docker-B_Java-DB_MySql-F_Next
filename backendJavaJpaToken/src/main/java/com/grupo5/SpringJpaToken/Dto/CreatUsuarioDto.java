package com.grupo5.SpringJpaToken.Dto;

import com.grupo5.SpringJpaToken.Types.Rol;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Data
@DynamicInsert
@DynamicUpdate
public class CreatUsuarioDto {
    private String nombre;
    private String username;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean status;
    private String password;
    private String UId;
    private Rol rol;
}
