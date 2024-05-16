package com.grupo5.SpringJpaToken.service;

import com.grupo5.SpringJpaToken.Relaciones.Previatura;
import com.grupo5.SpringJpaToken.Types.TypePrevia;
import com.grupo5.SpringJpaToken.model.Asignatura;
import com.grupo5.SpringJpaToken.repository.PreviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPreviaService {
    private final PreviaRepository previaRepository;

    @Autowired
    public IPreviaService(PreviaRepository previaRepository){
        this.previaRepository=previaRepository;
    }

    public Previatura altaPreviatura(Asignatura prev, Asignatura asignaturaOrigen, TypePrevia requisito){
        Previatura previatura= new Previatura();
        previatura.setAsignaturaPrevia(prev);
        previatura.setAsignaturaOrigen(asignaturaOrigen);
        previatura.setIdCarrera(asignaturaOrigen.getCarrera().getId());
        previatura.setRequisito(requisito);
        previatura.setNombrePrev(prev.getNombre());
        return previaRepository.save(previatura);
    }

    public void bajaPreviatura(Long prevId){
        previaRepository.deleteById(prevId);
    }




}
