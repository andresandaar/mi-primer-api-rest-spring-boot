package com.std.ec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.std.ec.model.dao.ClienteDao;
import com.std.ec.model.dto.ClienteDto;
import com.std.ec.model.entity.Cliente;
import com.std.ec.service.IClienteService;

@Service
public class ClienteImplService implements IClienteService {
    @Autowired
    private ClienteDao clienteDao;

    @Transactional
    @Override
    public Cliente save(ClienteDto clienteDto) {
        Cliente cliente = Cliente.builder()
        .idCliente(clienteDto.getIdCliente())
        .nombre(clienteDto.getNombre())
        .apellido(clienteDto.getApellido())
        .fechaRegistro(clienteDto.getFechaRegistro())
        .correo(clienteDto.getCorreo())
        .build();
        return clienteDao.save(cliente);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Integer idCliente) {
        return clienteDao.findById(idCliente).orElse(null);
    }

    @Transactional
    @Override
    public Boolean existsById(Integer idCliente) {
       return clienteDao.existsById(idCliente);
    }

    @Transactional
    @Override
    public void deleteById(Integer idCliente) {
       clienteDao.deleteById(idCliente);
    }

    @Override
    public List<Cliente> findAll() {
       return  (List<Cliente>) clienteDao.findAll();
    }

}
