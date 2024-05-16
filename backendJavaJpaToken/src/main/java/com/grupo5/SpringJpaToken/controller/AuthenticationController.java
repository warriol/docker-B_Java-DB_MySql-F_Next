package com.grupo5.SpringJpaToken.controller;

import com.grupo5.SpringJpaToken.config.ConexionFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.grupo5.SpringJpaToken.Dto.CreatUsuarioDto;
import com.grupo5.SpringJpaToken.Dto.FireBaseAuth;
import com.grupo5.SpringJpaToken.Dto.LoginRequest;
import com.grupo5.SpringJpaToken.Response.AuthenticationResponse;
import com.grupo5.SpringJpaToken.Response.MensajeComun;
import com.grupo5.SpringJpaToken.Types.Rol;
import com.grupo5.SpringJpaToken.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;
    private final ConexionFirebase conexionFirebase;


    @Autowired
    public AuthenticationController(AuthenticationService authService, ConexionFirebase conexionFirebase) {
        this.authService = authService;
        this.conexionFirebase = conexionFirebase;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody CreatUsuarioDto request
    ) {
        if(request.getRol() == null){
            request.setRol(Rol.USUARIO);
        }
        return ResponseEntity.ok(authService.registerUsuario(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        AuthenticationResponse auth=authService.authenticateUsuario(request);
        if (auth.getToken() == null || auth.getToken().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(auth);
        }
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<MensajeComun> verifyToken(@RequestBody String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            MensajeComun auth= authService.usuarioRegistradoGoogle(uid,email);

            return ResponseEntity.ok(auth);

        } catch (FirebaseAuthException e) {
            MensajeComun credentialsErrorResponse = new MensajeComun(null, "Error al verificar el token","error");
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(credentialsErrorResponse);
        }
    }

    @PostMapping("/registerTokenGoogle")
    public ResponseEntity<AuthenticationResponse> registerTokenGoogle(@RequestBody FireBaseAuth user) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(user.getIdToken());
            user.setUId(decodedToken.getUid());
            user.setEmail(decodedToken.getEmail());

            return ResponseEntity.ok(authService.regitrarUsuarioGoogle(user));

        } catch (FirebaseAuthException e) {
            AuthenticationResponse credentialsErrorResponse = new AuthenticationResponse(null, "Error al verificar el token");
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(credentialsErrorResponse);
        }
    }

    @PostMapping("/vincularU")
    public ResponseEntity<MensajeComun> vincularU(@RequestBody FireBaseAuth user){
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(user.getIdToken());
            MensajeComun mensaje= authService.vinculoGoogle(decodedToken.getUid(),decodedToken.getEmail(),user.getPassword());
            if (mensaje.getToken() == null || mensaje.getToken().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mensaje);
            }
            return ResponseEntity.ok(mensaje);
        }catch (FirebaseAuthException e){
            MensajeComun mensaje  = new MensajeComun(null, "Error al verificar el token","error");
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mensaje);
        }

    }
}
