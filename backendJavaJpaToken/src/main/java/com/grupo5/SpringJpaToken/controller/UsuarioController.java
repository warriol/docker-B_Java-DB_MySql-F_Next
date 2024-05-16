package com.grupo5.SpringJpaToken.controller;

import com.grupo5.SpringJpaToken.Dto.UsuarioDto;
import com.grupo5.SpringJpaToken.model.Usuario;
import com.grupo5.SpringJpaToken.service.IUsuarioService;
import com.grupo5.SpringJpaToken.service.JwtService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {
    private final IUsuarioService usuarioService;
    @Autowired
    private JwtService jwtTokenUtil;
    public UsuarioController(IUsuarioService usuarioService, JwtService jwtTokenUtil){
        this.usuarioService=usuarioService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PutMapping("editUser")
    public ResponseEntity<UsuarioDto> editPerfil(
            @RequestBody UsuarioDto request
    ){
        return usuarioService.editUser(request);
    }

    @GetMapping("getUsuario")
    public ResponseEntity<UsuarioDto> getUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication){
        String token = authentication.substring(7);
        Long id = jwtTokenUtil.extractId(token);

        UsuarioDto user= usuarioService.getUsuario(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


}
