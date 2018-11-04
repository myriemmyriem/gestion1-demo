package com.gestion.gestion1demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
public class utilisateur implements UserDetails , Serializable {


	@Id
	@Column(name = "Ident")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "passwd")
	private String password;

	@Column(name = "first_name")
	private String nom;

	@Column(name = "photo")
	private String photo;

	@Column(name = "last_name")
	private String prenom;

	@Column(name = "email")
	private String email;

	@Column(name = "phone_number")
	private String telephone;

	@Column(name = "enabled")
	private boolean enabled;

	// @Temporal(TemporalType.DATE)
	@Column(name = "last_password_reset_date")
	private Date lastPasswordResetDate;

	@Column(name = "profil")
	private String profil;

	@Column(name = "language")
	private String langue;

	/************************************************************************************/
	@ManyToOne
	private authority authorities;

	public utilisateur ()
	{

	}




	public utilisateur(Long id, String username, String password, String nom, String photo, String prenom, String email,
					   String telephone, String profil) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nom = nom;
		this.photo = photo;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.profil = profil;
	}




	public Long getId() {
		return id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	@JsonIgnore
	public String getLangue() {
		return langue;
	}


	public void setLangue(String langue) {
		this.langue = langue;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(authority authorities)
	{
		this.authorities = authorities;
	}

	@JsonIgnore
	public authority getAuth() {
		return this.authorities;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<authority> auth = new ArrayList<authority>();
		auth.add(authorities);
		return auth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public Boolean getEnabled()
	{
		return enabled;
	}
	@JsonIgnore
	public Date getLastPasswordResetDate()
	{
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate)
	{
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}




	public void setProfil(String profil) {
		this.profil = profil;
	}


	public String getProfil() {
		return profil;
	}


	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", username=" + username + ", password=" + password + ", nom=" + nom
				+ ", photo=" + photo + ", prenom=" + prenom + ", email=" + email + ", telephone=" + telephone
				+ ", enabled=" + enabled + ", lastPasswordResetDate=" + lastPasswordResetDate + ", profil=" + profil
				+ ", langue=" + langue + ", authorities=" + authorities + "]";
	}








}

