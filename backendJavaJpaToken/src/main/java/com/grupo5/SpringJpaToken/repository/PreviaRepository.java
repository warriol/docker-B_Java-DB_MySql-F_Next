package com.grupo5.SpringJpaToken.repository;

import com.grupo5.SpringJpaToken.Relaciones.Previatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreviaRepository extends JpaRepository<Previatura, Long> {

    Optional<Previatura> findById(Long id);

    void deleteById(Long id);

    List<Previatura> findByIdCarrera(Long IdCarrera);


}
