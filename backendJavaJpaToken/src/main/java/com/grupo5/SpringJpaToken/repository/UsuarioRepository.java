package com.grupo5.SpringJpaToken.repository;

import com.grupo5.SpringJpaToken.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.UIDGoogle = :uid OR u.email = :email")
    Optional<Usuario> findByUidOrEmail(@Param("uid") String uid, @Param("email") String email);
}
