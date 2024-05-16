package com.grupo5.SpringJpaToken.repository;

import com.grupo5.SpringJpaToken.model.Asignatura;
import com.grupo5.SpringJpaToken.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    Optional<Asignatura> findById(Long id);
    void deleteById(Long id);

    @Query("""
select a from Asignatura a 
where a.nombre = :nombre and a.carrera.id = :carreraId
""")
    Optional<Asignatura> repetidaAsignatura(@Param("nombre") String nombre, @Param("carreraId") Long carreraId);

    @Query("SELECT a FROM Asignatura a WHERE a.carrera.id = :carreraId")
    List<Asignatura> findByCarreraId(Long carreraId);
}
