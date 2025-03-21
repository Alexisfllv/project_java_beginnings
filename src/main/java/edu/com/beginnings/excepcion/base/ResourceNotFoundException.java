package edu.com.beginnings.excepcion.base;

import edu.com.beginnings.model.base.Libro;

public class ResourceNotFoundException extends RuntimeException {

    //excepcion 1

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    //
    public ResourceNotFoundException(String resourcedName, String fieldName , Object fieldValue) {

        //
        super(String.format("%s no encontrado con %s: '%s'", resourcedName, fieldName, fieldValue));
    }
}
