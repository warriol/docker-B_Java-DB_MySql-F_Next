package com.grupo5.SpringJpaToken.service;

import com.grupo5.SpringJpaToken.Dto.AsignaturaDto;
import com.grupo5.SpringJpaToken.Dto.PreviaDataDireccion;
import com.grupo5.SpringJpaToken.Dto.PreviasDataAsignatura;
import com.grupo5.SpringJpaToken.Relaciones.Previatura;
import com.grupo5.SpringJpaToken.Response.DataGrafo;
import com.grupo5.SpringJpaToken.Response.MensajeAltasComun;
import com.grupo5.SpringJpaToken.model.Asignatura;
import com.grupo5.SpringJpaToken.repository.AsignaturaRepository;
import com.grupo5.SpringJpaToken.repository.PreviaRepository;
import com.grupo5.SpringJpaToken.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IMetadataService {

    private final PreviaRepository previaRepository;
    private final AsignaturaRepository asignaturaRepository;

    public IMetadataService(PreviaRepository previaRepository, AsignaturaRepository asignaturaRepository) {
        this.previaRepository = previaRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    public DataGrafo getDataGrafo(Long idCarrera){
        try {

        List<Asignatura> asignatura = asignaturaRepository.findByCarreraId(idCarrera);
        if(asignatura==null)
            return new DataGrafo("No ahi asignaturas","warnning",null,null);
        List<Previatura> previaturas = previaRepository.findByIdCarrera(idCarrera);

        List<PreviasDataAsignatura> asignaturasData = asignatura.stream()
                .map(data -> {
                    PreviasDataAsignatura dataAsignatura = new PreviasDataAsignatura();
                    dataAsignatura.setId(data.getId());
                    dataAsignatura.setLabel(data.getNombre());
                    return dataAsignatura;
                })
                .collect(Collectors.toList());

        if(previaturas==null)
            return new DataGrafo("Se an obtenido los datos con exito","success",asignaturasData,null);

        List<PreviaDataDireccion> previaDataDireccions = previaturas.stream()
                .map(data -> {
                    PreviaDataDireccion fromto = new PreviaDataDireccion();
                    fromto.setFrom(data.getAsignaturaPrevia().getId());
                    fromto.setTo(data.getAsignaturaOrigen().getId());
                    fromto.setRequisito(data.getRequisito());
                    return fromto;
                })
                .collect(Collectors.toList());

        return new DataGrafo("Se an obtenido los datos con exito","success",asignaturasData,previaDataDireccions);
        }catch (DataIntegrityViolationException e){
            return new DataGrafo("Error: "+e, "error",null,null);
        }
    }
}
