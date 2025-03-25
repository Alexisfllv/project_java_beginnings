package edu.com.beginnings.excepcion.errores;

public class AccesoNoAutorizadoException extends RuntimeException {


    //const 401
    public AccesoNoAutorizadoException(String mensaje) {
        super(mensaje);
    }
}
