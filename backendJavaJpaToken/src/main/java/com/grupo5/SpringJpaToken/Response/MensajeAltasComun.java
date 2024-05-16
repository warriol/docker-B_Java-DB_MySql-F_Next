package com.grupo5.SpringJpaToken.Response;

public class MensajeAltasComun {
    private String message;
    private String evento;

    public MensajeAltasComun(String message, String evento) {
        this.message = message;
        this.evento = evento;
    }


    public String getMessage() {
        return message;
    }
    public String getEvento(){return evento;}
}
