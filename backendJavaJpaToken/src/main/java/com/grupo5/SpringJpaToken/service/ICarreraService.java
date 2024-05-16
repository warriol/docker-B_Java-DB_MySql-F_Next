package com.grupo5.SpringJpaToken.service;

import com.grupo5.SpringJpaToken.Dto.AsignaturaDto;
import com.grupo5.SpringJpaToken.Dto.CarreraDto;
import com.grupo5.SpringJpaToken.Response.MensajeAltasComun;
import com.grupo5.SpringJpaToken.model.Asignatura;
import com.grupo5.SpringJpaToken.model.Carrera;
import com.grupo5.SpringJpaToken.repository.CarreraRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

@Service
public class ICarreraService {

    private final CarreraRepository carreraRepository;
    private final IAsignaturaService asignaturaService;

    public ICarreraService(CarreraRepository carreraRepository, IAsignaturaService asignaturaService){
        this.carreraRepository=carreraRepository;
        this.asignaturaService=asignaturaService;
    }
    @Transactional
    public MensajeAltasComun altaCarrera(Carrera carrera) {
        try {
            List<Asignatura> newAsig= carrera.getAsignaturas();
            carrera.setAsignaturas(null);
            Carrera respuesta =carreraRepository.save(carrera);
            MensajeAltasComun mensaje= asignarAsignaturas(respuesta.getId(), newAsig);
            return mensaje;
        }catch (DataIntegrityViolationException e){
            return new MensajeAltasComun("Error: "+e, "error");
        }
    }

    public MensajeAltasComun asignarAsignaturas(Long idCarrera, List<Asignatura> asignaturas) {
        if(asignaturas==null)
            return new MensajeAltasComun("Se a dado de alta con exito el curso: ","success");
        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        List<Asignatura> asignaturasNuevas = new ArrayList<>();
        List<Asignatura> asignaturasOmitidas = new ArrayList<>();

        for (Asignatura asignatura : asignaturas) {
            Asignatura nuevaAsignatura = asignaturaService.altaAsignatura(asignatura, carrera);
            if (nuevaAsignatura != null) {
                asignaturasNuevas.add(nuevaAsignatura);
            } else {
                asignaturasOmitidas.add(asignatura);
            }
        }
        carrera.setAsignaturas(asignaturasNuevas);
        Carrera carreraReturn = carreraRepository.save(carrera);

        String mensaje = "Se ha dado de alta con éxito el curso: " + carreraReturn.getNombre();
        if (!asignaturasOmitidas.isEmpty()) {
            mensaje += "\nAsignaturas omitidas debido a duplicados:";
            for (Asignatura omitida : asignaturasOmitidas) {
                mensaje += "\n- " + omitida.getNombre();
            }
        }

        return new MensajeAltasComun(mensaje, "success");
    }


    public void bajaCarrera(Long carreraId) {
        carreraRepository.deleteById(carreraId);
    }

    public Carrera modificarCarrera(Long carreraId, Carrera carreraActualizada) {
        Carrera carreraExistente = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + carreraId));

        carreraExistente.setNombre(carreraActualizada.getNombre());
        carreraExistente.setDuracion(carreraActualizada.getDuracion());

        return carreraRepository.save(carreraExistente);
    }

    public CarreraDto getCarrera(Long idCarrera){
        Carrera carrera= carreraRepository.findById(idCarrera).orElse(null);
        if (carrera==null)
            return null;
        return carreraToCarreraDto(carrera);
    }

    private CarreraDto carreraToCarreraDto(Carrera carrera){
        CarreraDto carreraDTO = new CarreraDto();
        carreraDTO.setId(carrera.getId());
        carreraDTO.setNombre(carrera.getNombre());

        List<AsignaturaDto> asignaturasDTO = carrera.getAsignaturas().stream()
                .map(asignatura -> {
                    AsignaturaDto asignaturaDTO = new AsignaturaDto();
                    asignaturaDTO.setId(asignatura.getId());
                    asignaturaDTO.setNombre(asignatura.getNombre());
                    asignaturaDTO.setIdCarrera(asignatura.getCarrera().getId());
                    asignaturaDTO.setPreviaturas(null);
                    return asignaturaDTO;
                })
                .collect(Collectors.toList());

        carreraDTO.setAsignaturas(asignaturasDTO);
        return carreraDTO;
    }



}
