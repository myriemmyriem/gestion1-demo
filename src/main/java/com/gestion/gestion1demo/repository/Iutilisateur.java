package com.gestion.gestion1demo.repository;

import com.gestion.gestion1demo.model.utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Iutilisateur   extends JpaRepository<utilisateur, Long> {
	utilisateur findByUsername(String username);

	@Query("SELECT u FROM utilisateur u where u.username = :username")
	utilisateur getUser(@Param("username") String usename);

}


