package com.grupo5.SpringJpaToken.repository;

import com.grupo5.SpringJpaToken.model.Examen;
import com.grupo5.SpringJpaToken.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    Optional<Examen> findById(Long id);
    void deleteById(Long id);

}
