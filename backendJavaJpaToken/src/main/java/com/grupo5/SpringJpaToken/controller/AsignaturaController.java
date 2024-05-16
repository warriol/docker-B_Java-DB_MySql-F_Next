package com.grupo5.SpringJpaToken.controller;


import com.grupo5.SpringJpaToken.Dto.AsignaturaDto;
import com.grupo5.SpringJpaToken.Dto.CarreraDto;
import com.grupo5.SpringJpaToken.Dto.PreviaturaDto;
import com.grupo5.SpringJpaToken.Response.MensajeAltasComun;
import com.grupo5.SpringJpaToken.model.Asignatura;
import com.grupo5.SpringJpaToken.service.IAsignaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/asignatura")
public class AsignaturaController {

    private final IAsignaturaService iAsignaturaService;

    private AsignaturaController(IAsignaturaService iAsignaturaService) {
        this.iAsignaturaService = iAsignaturaService;
    }

    @PutMapping("/altaPrevia")
    private ResponseEntity<MensajeAltasComun> altaPrevia(@RequestBody PreviaturaDto prev) {
        MensajeAltasComun mensaje = iAsignaturaService.asignarPreviatura(prev);
        if (Objects.equals(mensaje.getEvento(), "error"))
            return ResponseEntity.badRequest().body(mensaje);
        return ResponseEntity.ok(mensaje);

    }

    @GetMapping("/getAsignatura")
    private ResponseEntity<AsignaturaDto> getAsignatura(@RequestParam Long idAsignatura){
        AsignaturaDto asignatura = iAsignaturaService.getAsignatura(idAsignatura);
        if (asignatura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(asignatura);
    }
}
