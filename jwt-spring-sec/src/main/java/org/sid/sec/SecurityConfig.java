package org.sid.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	//il faut instancier dans l'application car il n'est pas un bean
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	//comment vous chercher le utilisateurs et les roles:
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Créer des utilisateurs en mémoire :
		/*
		 * auth.inMemoryAuthentication() .withUser("admin") .password("1234")
		 * .roles("ADMIN", "USER") .and() .withUser("user") .password("1234")
		 * .roles("USER");
		 */
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);//quand tu va vérifier le mot de passe utiliser cette fonction
	}

	
	
	//définir les droits d'accés et les routes ++++ ajouter des filtres ou pas
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//désactiver l'authentification basée sur les sessions(références)  ->>>>>> Jwt(par valeur)
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//créer notre filtre
		http.formLogin();// une formulaire d'authentification utilisé par Spring
		//pour accéder à ca nécessite pas une authentification:
		http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
		//si une requéte avec POST et le lien /tasks/** il doit que 
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/**").hasAuthority("ADMIN");
		//toutes les ressources de l'application nécessitent une authentification 
		http.authorizeRequests().anyRequest().authenticated();
		//hériter authenticationManager(): l'objet principal d'authentication depuis WebSecurityConfigurerAdapter
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		http.addFilterBefore(new JWTAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class); 
	}
}
