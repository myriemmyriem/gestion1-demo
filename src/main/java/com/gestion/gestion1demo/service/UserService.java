package com.gestion.gestion1demo.service;

import com.gestion.gestion1demo.model.utilisateur;

import java.util.List;

public interface UserService {
	public utilisateur findById(Long id);

	public utilisateur findByUsername(String username);

	public List<utilisateur> findAll();
}
