package com.grupo5.SpringJpaToken.service;

import com.grupo5.SpringJpaToken.Dto.FireBaseAuth;
import com.grupo5.SpringJpaToken.Dto.UsuarioDto;
import com.grupo5.SpringJpaToken.Response.AuthenticationResponse;
import com.grupo5.SpringJpaToken.Response.MensajeComun;
import com.grupo5.SpringJpaToken.Types.Rol;
import com.grupo5.SpringJpaToken.model.Usuario;
import com.grupo5.SpringJpaToken.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IUsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final JwtService jwtService;


    public IUsuarioService(UsuarioRepository repository, JwtService jwtService ){
        this.repository=repository;
        this.jwtService=jwtService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No Exite Usuario"));
    }

    public ResponseEntity editUser(UsuarioDto user){
        Usuario usuario = repository.findById(user.getId()).orElse(null);
        if (usuario == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: La función no pudo ejecutarse correctamente");
        }else{
            try {
                usuario.setTelefono(user.getTelefono());
                usuario.setEmail(user.getEmail());
                usuario.setNombre(user.getNombre());
                usuario.setApellido((usuario.getApellido()));
                repository.save(usuario);
                return ResponseEntity.ok("Perfil de usuario actualizado correctamente");
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Nombre de usuario o correo electrónico ya existen");
            }
        }
    }

    public UsuarioDto getUsuario(Long id){
        Usuario usuario = repository.findById(id).orElse(null);
        if (usuario == null)
            return null;

        return  usurioToUsuarioDto(usuario);

    }

    private UsuarioDto usurioToUsuarioDto(Usuario usuario){
        UsuarioDto user = new UsuarioDto();
        user.setId(usuario.getId());
        user.setStatus(usuario.getStatus());
        user.setTelefono(usuario.getTelefono());
        user.setNombre(usuario.getNombre());
        user.setApellido(usuario.getApellido());
        user.setUsername(usuario.getUsername());
        user.setEmail(usuario.getEmail());
        user.setUIDGoogle(usuario.getUIDGoogle());
        user.setRol(Rol.USUARIO);

        return user;
    }


}
