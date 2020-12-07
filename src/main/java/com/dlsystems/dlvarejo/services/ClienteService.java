package com.dlsystems.dlvarejo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlsystems.dlvarejo.domain.Cliente;
import com.dlsystems.dlvarejo.repositories.ClienteRepository;
import com.dlsystems.dlvarejo.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService{

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
	}
