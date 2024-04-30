package com.std.ec.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.std.ec.exception.BadRequestException;
import com.std.ec.exception.ResourceNotFoundException;
import com.std.ec.model.dto.ClienteDto;
import com.std.ec.model.entity.Cliente;
import com.std.ec.model.payload.MensajeResponse;
import com.std.ec.service.IClienteService;

import jakarta.validation.Valid;

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
    public ResponseEntity<MensajeResponse> create(@RequestBody @Valid ClienteDto clienteDto) {
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
            throw new BadRequestException(ex.getMessage()); 
        }

    }

    @PutMapping("cliente/{idCliente}")
    public ResponseEntity<?> update(@RequestBody @Valid ClienteDto clienteDto, @PathVariable Integer idCliente) {

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
                throw new ResourceNotFoundException("Cliente","id",idCliente); 
            }

        } catch (DataAccessException ex) {
            // Manejar excepciones relacionadas con la base de datos
            throw new BadRequestException(ex.getMessage()); 

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
                throw new ResourceNotFoundException("Cliente","id",idCliente); 

            }

        } catch (DataAccessException ex) {

            // Si hay una excepción de acceso a datos, devuelve un ResponseEntity con un
            // mensaje y estado METHOD NOT ALLOWED
            throw new BadRequestException(ex.getMessage()); 
        }
    }

    @GetMapping("cliente/{idCliente}")
    public ResponseEntity<MensajeResponse> showById(@PathVariable Integer idCliente) {

        try {
            Cliente clienteById = clienteService.findById(idCliente);

            if (clienteById == null) {
                throw new ResourceNotFoundException("Cliente","id",idCliente);  
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
            throw new BadRequestException(ex.getMessage()); 
        }

    }

    @GetMapping("clientes")
    public ResponseEntity<MensajeResponse> showClientes() {
        try {
            List<Cliente> clientes = clienteService.findAll();

            if (clientes == null || clientes.isEmpty()) {
                throw new ResourceNotFoundException("Clientes");
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
            throw new BadRequestException(ex.getMessage()); 
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

}
