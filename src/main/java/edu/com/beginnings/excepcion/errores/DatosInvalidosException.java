package edu.com.beginnings.excepcion.errores;

public class DatosInvalidosException extends RuntimeException {


    //401
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
