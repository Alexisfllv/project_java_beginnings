package edu.com.beginnings.excepcion.base;

public class LibroRecursoNoEncontradoException extends RuntimeException {

    //consctructor
    public LibroRecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
