package com.std.ec.model.dto;

import java.io.Serializable;
import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ClienteDto implements Serializable {
    private Integer idCliente;
    @NotEmpty(message = "Nombre requerido")
    @Size(min = 2 ,max = 45)
    private String nombre;

    @NotEmpty(message = "Apellido requerido")
    @Size(min = 2 ,max = 45)
    private String apellido;

    @NotEmpty(message = "Correo requerido")
    @Email
    private String correo;
    private Date fechaRegistro;
}
