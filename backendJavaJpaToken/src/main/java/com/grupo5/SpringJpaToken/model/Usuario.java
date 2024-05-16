package com.grupo5.SpringJpaToken.model;

import com.grupo5.SpringJpaToken.Relaciones.InscripcionExamen;
import com.grupo5.SpringJpaToken.Types.Rol;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "UIDGoogle",unique=true, nullable = true)
    private String UIDGoogle;

    @Column(name = "username",unique=true)
    private String username;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email",unique=true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "status")
    private Boolean status;

    @Enumerated(value = EnumType.STRING)
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private List<Token> tokens;

    // Relación muchos a uno con InscripcionExamen
    @ManyToOne
    @JoinColumn(name = "inscripcion_examen_id")
    private InscripcionExamen inscripcionExamen;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

