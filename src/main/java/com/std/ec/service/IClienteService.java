package com.std.ec.service;

import java.util.List;

import com.std.ec.model.dto.ClienteDto;
import com.std.ec.model.entity.Cliente;

public interface IClienteService {

    Cliente save(ClienteDto cliente);

    Cliente findById(Integer idCliente);

    void deleteById(Integer idCliente);

    Boolean existsById(Integer idCliente);

    List<Cliente> findAll();

}
