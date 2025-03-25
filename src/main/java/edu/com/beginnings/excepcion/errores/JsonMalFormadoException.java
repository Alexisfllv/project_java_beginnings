package edu.com.beginnings.excepcion.errores;

public class JsonMalFormadoException extends RuntimeException {

    public JsonMalFormadoException(String mensaje) {
        super(mensaje);
    }
}
