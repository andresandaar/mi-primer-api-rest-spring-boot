package com.std.ec.service;

import com.std.ec.model.entity.Cliente;

public interface ICliente {

    Cliente save(Cliente cliente);

    Cliente findById(Integer idCliente);

    void delete(Cliente cliente);

}
