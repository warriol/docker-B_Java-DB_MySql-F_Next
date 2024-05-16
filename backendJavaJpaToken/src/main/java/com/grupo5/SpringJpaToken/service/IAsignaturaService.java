package com.grupo5.SpringJpaToken.service;

import com.grupo5.SpringJpaToken.Dto.AsignaturaDto;
import com.grupo5.SpringJpaToken.Dto.PreviaturaDto;
import com.grupo5.SpringJpaToken.Relaciones.Previatura;
import com.grupo5.SpringJpaToken.Response.MensajeAltasComun;
import com.grupo5.SpringJpaToken.model.Asignatura;
import com.grupo5.SpringJpaToken.model.Carrera;
import com.grupo5.SpringJpaToken.repository.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IAsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    private final IPreviaService iPreviaService;

    @Autowired
    public IAsignaturaService(AsignaturaRepository asignaturaRepository, IPreviaService iPreviaService) {
        this.asignaturaRepository = asignaturaRepository;
        this.iPreviaService = iPreviaService;
    }

    public Asignatura altaAsignatura(Asignatura asignatura, Carrera carrera) {
        Asignatura asignaturaExistente = asignaturaRepository.repetidaAsignatura(asignatura.getNombre(), carrera.getId())
                .orElse(null);
        if (asignaturaExistente != null) {
            return null;
        }
        asignatura.setCarrera(carrera);
        return asignaturaRepository.save(asignatura);
    }

    public MensajeAltasComun asignarPreviatura(PreviaturaDto previaturaDto){
        try {
            Asignatura asignatura = asignaturaRepository.findById(previaturaDto.getIdAsigOrigen()).orElseThrow();
            Asignatura previaSelect= asignaturaRepository.findById(previaturaDto.getIdPrevAsignada()).orElseThrow();

            Previatura prev= iPreviaService.altaPreviatura(previaSelect,asignatura,previaturaDto.getRequisito());

            return new MensajeAltasComun("Se asignado la previa con exito","success");


        }catch (DataIntegrityViolationException e){
            return new MensajeAltasComun("Error: "+e, "error");
        }
    }

    public void bajaAsignatura(Long asignaturaId) {
        asignaturaRepository.deleteById(asignaturaId);
    }

    public Asignatura modificarAsignatura(Long asignaturaId, Asignatura asignaturaActualizada) {
        Asignatura asignaturaExistente = asignaturaRepository.findById(asignaturaId)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + asignaturaId));

        asignaturaExistente.setNombre(asignaturaActualizada.getNombre());


        return asignaturaRepository.save(asignaturaExistente);
    }

    public AsignaturaDto getAsignatura(Long idAsignatura){
        Asignatura asignatura = asignaturaRepository.findById(idAsignatura).orElse(null);
        if(asignatura == null)
            return null;
        return asignaturaToAsignaturaDto(asignatura);
    }

    public AsignaturaDto asignaturaToAsignaturaDto(Asignatura asignatura){
        AsignaturaDto asignaturaDto = new AsignaturaDto();
        asignaturaDto.setId(asignatura.getId());
        asignaturaDto.setIdCarrera(asignatura.getCarrera().getId());
        asignaturaDto.setNombre(asignatura.getNombre());

        List<PreviaturaDto> previaturaDto = asignatura.getPreviaturas().stream()
                .map(previatura -> {
                    PreviaturaDto previaturaDTO = new PreviaturaDto();
                    previaturaDTO.setIdAsigOrigen(previatura.getAsignaturaOrigen().getId());
                    previaturaDTO.setIdPrevAsignada(previatura.getAsignaturaPrevia().getId());
                    previaturaDTO.setRequisito(previatura.getRequisito());
                    previaturaDTO.setNombrePrev(previatura.getNombrePrev());
                    return previaturaDTO;
                })
                .collect(Collectors.toList());

        asignaturaDto.setPreviaturas(previaturaDto);
        return asignaturaDto;
    }
}
