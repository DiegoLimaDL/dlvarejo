package com.dlsystems.dlvarejo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlsystems.dlvarejo.domain.Categoria;
import com.dlsystems.dlvarejo.repositories.CategoriaRepository;
import com.dlsystems.dlvarejo.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService{

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}
	}
