package com.geovivienda.geovivienda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UsuarioInactivoException extends RuntimeException {
    public UsuarioInactivoException(String mensaje) {
        super(mensaje);
    }
}
