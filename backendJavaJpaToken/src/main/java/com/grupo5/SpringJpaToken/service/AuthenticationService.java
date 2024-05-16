package com.grupo5.SpringJpaToken.service;


import com.grupo5.SpringJpaToken.Dto.CreatUsuarioDto;
import com.grupo5.SpringJpaToken.Dto.FireBaseAuth;
import com.grupo5.SpringJpaToken.Dto.LoginRequest;
import com.grupo5.SpringJpaToken.Response.AuthenticationResponse;
import com.grupo5.SpringJpaToken.Response.MensajeComun;
import com.grupo5.SpringJpaToken.Types.Rol;
import com.grupo5.SpringJpaToken.model.Token;
import com.grupo5.SpringJpaToken.model.Usuario;
import com.grupo5.SpringJpaToken.repository.TokenRepository;
import com.grupo5.SpringJpaToken.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager,
                                 UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    public AuthenticationResponse registerUsuario(CreatUsuarioDto newuser) {

        if(usuarioRepository.findByUsername(newuser.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "Usuario ya existe");
        }
        Usuario user= crearUsuarioDtotoUsuario(newuser);

        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = usuarioRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return new AuthenticationResponse(null, "Email Utilizado");
        }
        String jwt = jwtService.generateTokenTest(user);

        saveUserTokenTest(jwt, user);

        return new AuthenticationResponse(jwt, "Usuario registrado con exito");

    }

    private Usuario crearUsuarioDtotoUsuario(CreatUsuarioDto creatUsuarioDto){
        Usuario user = new Usuario();
        user.setStatus(creatUsuarioDto.getStatus());
        user.setTelefono(creatUsuarioDto.getTelefono());
        user.setNombre(creatUsuarioDto.getNombre());
        user.setApellido(creatUsuarioDto.getApellido());
        user.setUsername(creatUsuarioDto.getUsername());
        user.setEmail(creatUsuarioDto.getEmail());
        user.setPassword(creatUsuarioDto.getPassword());
        user.setUIDGoogle(creatUsuarioDto.getUId());
        user.setRol(Rol.USUARIO);

        return user;
    }

    public AuthenticationResponse authenticateUsuario(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }catch(AuthenticationException e){
            return new AuthenticationResponse(null, "Credenciales erroneas");
        }

        Usuario user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateTokenTest(user);

        revokeAllTokenByUserTest(user);//Esta funcion basicamente resetea los tokens exitentes para el usuario,o si incia en otro lado
        saveUserTokenTest(jwt, user);//Esta inicializa el nuevo token

        return new AuthenticationResponse(jwt, "Bienvenido " + user.getUsername() + " 😄");

    }
    private void revokeAllTokenByUserTest(Usuario user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserTokenTest(String jwt, Usuario user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUsuario(user);
        tokenRepository.save(token);
    }

    public MensajeComun usuarioRegistradoGoogle(String uid, String email ){
        Usuario usuario = usuarioRepository.findByUidOrEmail(uid,email).orElse(null);
        if(usuario == null){
            return new MensajeComun(null, "No esta Registrado", "success");
        }else if(usuario.getUIDGoogle()!=null){
            String jwt = jwtService.generateTokenTest(usuario);
            return new MensajeComun(jwt,"Bienvenido " + usuario.getUsername() + " 😄","success");
        }else{//caso el correo electronico unico coincidio con el de google
            return new MensajeComun(null,"Desea vincular tu cuenta con google\n "+usuario.getUsername(),"question");
        }
    }

    public AuthenticationResponse regitrarUsuarioGoogle(FireBaseAuth userG){

        CreatUsuarioDto user = new CreatUsuarioDto();
        user.setStatus(true);
        user.setTelefono(userG.getTelefono());
        user.setNombre(userG.getNombre());
        user.setApellido(userG.getApellido());
        user.setUsername(userG.getUsername());
        user.setEmail(userG.getEmail());
        user.setPassword(userG.getPassword());
        user.setUId(userG.getUId());
        user.setRol(Rol.USUARIO);

        return registerUsuario(user);
    }

    public MensajeComun vinculoGoogle(String uid, String email, String passward){
        Usuario usuario = usuarioRepository.findByUidOrEmail(uid,email).orElse(null);


        if (usuario == null){
            return new MensajeComun(null, "Credenciales erroneas", "error");

        }else{
            try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getUsername(),
                            passward
                    )
            );
            }catch(AuthenticationException e){
                return new MensajeComun(null, "Credenciales erroneas", "error");
            }
            usuario.setUIDGoogle(uid);
            usuario=usuarioRepository.save(usuario);

            String jwt = jwtService.generateTokenTest(usuario);

            revokeAllTokenByUserTest(usuario);
            saveUserTokenTest(jwt, usuario);

            return new MensajeComun(jwt, "Vinculo realizado con exito", "succes");

        }
    }
}
