package com.std.ec.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.std.ec.model.dto.ClienteDto;
import com.std.ec.model.entity.Cliente;
import com.std.ec.model.payload.MensajeResponse;
import com.std.ec.service.IClienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping("cliente")
    public ResponseEntity<MensajeResponse> create(@RequestBody ClienteDto clienteDto) {
        Cliente clienteSave = null;
        try {

            clienteSave = clienteService.save(clienteDto);

            ClienteDto clienteResponseDto = buildClienteDto(clienteSave);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MensajeResponse.builder()
                            .mensaje("Guardado correctamente")
                            .object(clienteResponseDto)
                            .build());

        } catch (DataAccessException ex) {
            // Manejar excepciones relacionadas con la base de datos
            return handleDataAccessException(ex);
        }

    }

    @PutMapping("cliente/{idCliente}")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable Integer idCliente) {

        Cliente clienteUpdate = null;
        try {
            if (clienteService.existsById(idCliente)) {

                clienteDto.setIdCliente(idCliente);
                clienteUpdate = clienteService.save(clienteDto);

                // Construir la respuesta exitosa
                ClienteDto clienteResponseDto = buildClienteDto(clienteUpdate);

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(MensajeResponse.builder()
                                .mensaje("Actualizado correctamente")
                                .object(clienteResponseDto)
                                .build());

            } else {
                // El registro no se encuentra en la base de datos
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos!!")
                                .object(null)
                                .build());
            }

        } catch (DataAccessException ex) {
            // Manejar excepciones relacionadas con la base de datos
            return handleDataAccessException(ex);

        }
    }

    @DeleteMapping("cliente/{idCliente}")
    public ResponseEntity<MensajeResponse> delete(@PathVariable Integer idCliente) {

        try {

            if (clienteService.existsById(idCliente)) {
                // Eliminar el cliente si existe
                clienteService.deleteById(idCliente);

                // Devuelve un ResponseEntity con estado NO CONTENT
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(MensajeResponse.builder()
                                .mensaje("Cliente eliminado correctamente")
                                .object(null)
                                .build());
            } else {
                // Si el cliente no existe, devuelve un ResponseEntity con un mensaje y estado
                // NOT FOUND
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(MensajeResponse.builder()
                                .mensaje("El registro no existe!!")
                                .object(null)
                                .build());
            }

        } catch (DataAccessException ex) {

            // Si hay una excepción de acceso a datos, devuelve un ResponseEntity con un
            // mensaje y estado METHOD NOT ALLOWED
            return handleDataAccessException(ex);
        }
    }

    @GetMapping("cliente/{idCliente}")
    public ResponseEntity<MensajeResponse> showById(@PathVariable Integer idCliente) {

        try {
            Cliente clienteById = clienteService.findById(idCliente);

            if (clienteById == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(MensajeResponse.builder()
                                .mensaje("El registro no existe!!")
                                .object(null)
                                .build());
            }

            // Construir la respuesta exitosa
            ClienteDto clienteDto = buildClienteDto(clienteById);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MensajeResponse.builder()
                            .mensaje("")
                            .object(clienteDto)
                            .build());

        } catch (DataAccessException ex) {
            // Si hay una excepción de acceso a datos, devuelve un ResponseEntity con un
            // mensaje y estado METHOD NOT ALLOWED
            return handleDataAccessException(ex);
        }

    }

    @GetMapping("clientes")
    public ResponseEntity<MensajeResponse> showClientes() {
        try {
            List<Cliente> clientes = clienteService.findAll();

            if (clientes == null || clientes.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(MensajeResponse.builder()
                                .mensaje("No hay registros!!")
                                .object(null)
                                .build());
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MensajeResponse.builder()
                            .mensaje("Clientes encontrados correctamente")
                            .object(clientes)
                            .build());

        } catch (DataAccessException ex) {
            // Si hay una excepción de acceso a datos, devuelve un ResponseEntity con un
            // mensaje y estado INTERNAL SERVER ERROR
            return handleDataAccessException(ex);
        }
    }

    private ClienteDto buildClienteDto(Cliente cliente) {
        return ClienteDto.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .correo(cliente.getCorreo())
                .fechaRegistro(cliente.getFechaRegistro())
                .build();
    }

    private ResponseEntity<MensajeResponse> handleDataAccessException(DataAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MensajeResponse.builder()
                        .mensaje("Error al acceder a la base de datos: " + ex.getMessage())
                        .object(null)
                        .build());
    }

}
