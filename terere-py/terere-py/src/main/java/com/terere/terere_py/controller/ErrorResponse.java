
// ErrorResponse.java - Clase auxiliar para errores
package com.terere.terere_py.controller;

public class ErrorResponse {
    private String mensaje;
    
    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}