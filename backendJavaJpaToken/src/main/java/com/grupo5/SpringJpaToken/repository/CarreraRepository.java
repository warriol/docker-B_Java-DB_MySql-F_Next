package com.grupo5.SpringJpaToken.repository;

import com.grupo5.SpringJpaToken.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {//No se si anda el long
    Optional<Carrera> findById(Long id);
    void deleteById(Long id);
}
