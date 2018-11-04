package com.gestion.gestion1demo.controller;


import com.gestion.gestion1demo.model.authority;
import com.gestion.gestion1demo.model.utilisateur;
import com.gestion.gestion1demo.model.utilisateurTokenState;
import com.gestion.gestion1demo.repository.Iauthority;
import com.gestion.gestion1demo.repository.Iutilisateur;
import com.gestion.gestion1demo.security.TokenHelper;
import com.gestion.gestion1demo.security.auth.JwtAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Transactional
@CrossOrigin("*")
	
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	Iutilisateur userRepository;
	@Autowired
	Iauthority iAuthority;

	@Autowired
	TokenHelper tokenHelper;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		System.out.println(userRepository.findByUsername(authenticationRequest.getUsername()).toString());
		//System.out.println(userRepository.findByUsername(authenticationRequest.getUsername()).getAuthorities().size());
		System.out.println(authenticationRequest.getUsername()+ authenticationRequest.getPassword());
		// Perf the security
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		
		// Inject into security context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// token creation
		utilisateur user = (utilisateur) authentication.getPrincipal();
		
		String jws = tokenHelper.generateToken(user.getUsername());
		
		int expiresIn = tokenHelper.getExpiredIn();
		// Add c..0ie to response
		response.addCookie(createAuthCookie(jws, expiresIn));
		
		// Return the token
		return ResponseEntity.ok(new utilisateurTokenState(jws, expiresIn));
	}

	
	
	private Cookie createAuthCookie(String jwt, int expiresIn) {
		Cookie authCookie = new Cookie(tokenHelper.AUTH_COOKIE, (jwt));
		authCookie.setPath("/");
		authCookie.setHttpOnly(true);
		authCookie.setMaxAge(expiresIn);
		return authCookie;
	}
	
	@GetMapping("/getrolebyname")
	public @ResponseBody
	authority getUserByName(String name)
	{
		System.out.println(name);
		return iAuthority.findByName(name);
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public  @ResponseBody Map<String, Boolean> apdateuser (@RequestBody utilisateur u) {
		Boolean response;
		
		try {
			utilisateur u2 = userRepository.getOne(u.getId());
			u.setAuthorities(u2.getAuth());
            // Authority a= iAuthority.findByName("ROLE_"+u.getProfil());
			Date d=new Date();
			u.setEnabled(true);
			u.setLangue("fr");
			userRepository.save(u); 
           Map<String, Boolean> success = new TreeMap<String, Boolean>();
			success.put("response", true);
			return success;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			//e.printStackTrace();
			Map<String, Boolean> echec = new TreeMap<String, Boolean>();
			echec.put("response", false);
			return echec;
		}
	}
	
	

	
		
	
	
	
	
}