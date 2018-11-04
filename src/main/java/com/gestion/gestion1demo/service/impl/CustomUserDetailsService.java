package com.gestion.gestion1demo.service.impl;

import com.gestion.gestion1demo.model.utilisateur;
import com.gestion.gestion1demo.repository.Iutilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private Iutilisateur userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		utilisateur user = userRepository.findByUsername(username);
		if (user == null) 
		{
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}

}
