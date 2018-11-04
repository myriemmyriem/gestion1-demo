package com.gestion.gestion1demo.repository;

import com.gestion.gestion1demo.model.authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Iauthority extends JpaRepository<authority, Long>{

	authority findByName(String name);

}


