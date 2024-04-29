package com.std.ec.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.std.ec.model.entity.Cliente;

public interface ClienteDao  extends CrudRepository<Cliente,Integer>{

}
