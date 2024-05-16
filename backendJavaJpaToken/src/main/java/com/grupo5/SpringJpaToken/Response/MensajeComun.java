package com.grupo5.SpringJpaToken.Response;

public class MensajeComun {

    private String token;
    private String message;
    private String evento;

    public MensajeComun(String token, String message, String evento) {
        this.token = token;
        this.message = message;
        this.evento = evento;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
    public String getEvento(){return evento;}
}

