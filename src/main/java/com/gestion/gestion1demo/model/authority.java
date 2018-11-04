package com.gestion.gestion1demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity

public class authority implements GrantedAuthority, Serializable{



	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;


	@OneToMany(mappedBy = "authorities")
	private List<utilisateur> visiteurs;


	@Column(name="name")
	String name;

	@Override
	public String getAuthority() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public List<utilisateur> getvisiteurs() {
		return visiteurs;
	}

	public void setvisiteurs(List<utilisateur> visiteurs) {
		this.visiteurs = visiteurs;
	}


}
