package com.grupo5.SpringJpaToken.service;

import com.grupo5.SpringJpaToken.model.Examen;
import com.grupo5.SpringJpaToken.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IExamenService {

    private final ExamenRepository examenRepository;

    @Autowired
    public IExamenService(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    public Examen altaExamen(Examen examen) {
        return examenRepository.save(examen);
    }

    public void bajaExamen(Long examenId) {
        examenRepository.deleteById(examenId);
    }

    public Examen modificarExamen(Long examenId, Examen examenActualizado) {
        Examen examenExistente = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado con ID: " + examenId));

        examenExistente.setTipo(examenActualizado.getTipo());
        // Agregar más atributos según sea necesario

        return examenRepository.save(examenExistente);
    }
}
