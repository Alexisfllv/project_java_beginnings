package edu.com.beginnings.mensaje;

import lombok.Getter;

@Getter
public enum MensajeRespuesta {
    MODIFICACION_EXITOSA("Modificación realizada con éxito"),
    ELIMINACION_EXITOSA("Eliminación realizada con éxito"),
    AGREGADO_EXITOSO("Agregado correctamente");

    private final String mensaje;

    // Constructor privado
    MensajeRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }
}
