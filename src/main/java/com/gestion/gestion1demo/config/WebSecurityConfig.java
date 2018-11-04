package com.gestion.gestion1demo.config;

import com.gestion.gestion1demo.security.TokenHelper;
import com.gestion.gestion1demo.security.auth.LogoutSuccess;
import com.gestion.gestion1demo.security.auth.RestAuthenticationEntryPoint;
import com.gestion.gestion1demo.security.auth.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Transactional
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	private LogoutSuccess logoutSuccess;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Autowired
	TokenHelper tokenHelper;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/api/user/getall").hasRole("deny");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint).and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers("/auth/**", "http://localhost:4200").permitAll().anyRequest()
				.authenticated().and()
				.addFilterBefore(new TokenAuthenticationFilter(tokenHelper, userDetailsService),
						BasicAuthenticationFilter.class)
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
				.logoutSuccessHandler(logoutSuccess).deleteCookies(TOKEN_COOKIE);


		http.csrf().disable();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter will ignore the below paths
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/**/*.html", "/**/*.css",
				"/**/*.js");

	}
}

