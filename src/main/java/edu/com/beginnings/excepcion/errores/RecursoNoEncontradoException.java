package edu.com.beginnings.excepcion.errores;

public class RecursoNoEncontradoException  extends RuntimeException {

    //constructor 404
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
