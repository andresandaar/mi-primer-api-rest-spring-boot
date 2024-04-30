package com.std.ec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    
    @SuppressWarnings("unused")
    private  String resourceName;
    @SuppressWarnings("unused")
    private String fielName;
    @SuppressWarnings("unused")
    private Object fielValue;

    public ResourceNotFoundException( String resourceName, String fielName, Object fielValue) {
        super(String.format("%s no fue encontrado con: %s='%s'", resourceName,fielName,fielValue));
        this.resourceName = resourceName;
        this.fielName = fielName;
        this.fielValue = fielValue;
    }

    public ResourceNotFoundException( String resourceName) {
        super(String.format("No hay registros de %s en el sistema.", resourceName));
        this.resourceName = resourceName;
    }

}
