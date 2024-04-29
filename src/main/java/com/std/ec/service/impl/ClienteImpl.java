package com.std.ec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.std.ec.model.dao.ClienteDao;
import com.std.ec.model.entity.Cliente;
import com.std.ec.service.ICliente;

@Service
public class ClienteImpl implements ICliente {
    @Autowired
    private ClienteDao clienteDao;

    @Transactional
    @Override
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Integer idCliente) {
        return clienteDao.findById(idCliente).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }

}
