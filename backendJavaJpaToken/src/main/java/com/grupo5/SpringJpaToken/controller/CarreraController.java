package com.grupo5.SpringJpaToken.controller;

import com.grupo5.SpringJpaToken.Dto.CarreraDto;
import com.grupo5.SpringJpaToken.Response.MensajeAltasComun;
import com.grupo5.SpringJpaToken.model.Carrera;
import com.grupo5.SpringJpaToken.service.ICarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "curso")
public class CarreraController {
    private final ICarreraService carreraService;

    public CarreraController(ICarreraService carreraService){
        this.carreraService=carreraService;
    }

    @PostMapping("/alta")
    public ResponseEntity<MensajeAltasComun> alta(@RequestBody Carrera carrera){
        MensajeAltasComun mensaje= carreraService.altaCarrera(carrera);
        if(Objects.equals(mensaje.getEvento(), "error"))
            return ResponseEntity.badRequest().body(mensaje);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/getCarrera")
    public ResponseEntity<CarreraDto> getCarrera(@RequestParam Long idCarrera){
        CarreraDto carrera = carreraService.getCarrera(idCarrera);
        if (carrera == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrera);
    }

}
