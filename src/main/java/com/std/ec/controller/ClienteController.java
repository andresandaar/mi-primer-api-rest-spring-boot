package com.std.ec.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.std.ec.model.entity.Cliente;
import com.std.ec.service.ICliente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private ICliente clienteService;

    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente update( @RequestBody  Cliente cliente) {
        return clienteService.save(cliente);
    }

    @DeleteMapping("cliente/{idCliente}")
    public ResponseEntity<?> delete(@PathVariable Integer idCliente) {
        Map<String, Object>  response =new HashMap<>();

        try {
            Cliente clienteDelete = clienteService.findById(idCliente);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            response.put("mensaje",exDt.getMessage());
            response.put("cliente",null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            
        }
    }

    @GetMapping("cliente/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente showById(@PathVariable  Integer idCliente) {
     
        return clienteService.findById(idCliente);

    }

}
