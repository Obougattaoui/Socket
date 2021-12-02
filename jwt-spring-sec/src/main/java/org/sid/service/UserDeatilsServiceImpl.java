package org.sid.service;

import java.util.ArrayList;
import java.util.Collection;

import org.sid.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDeatilsServiceImpl implements UserDetailsService{
	@Autowired
	private AccountService accountService;
	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//cherche moi un utilisateur :
		AppUser user = accountService.findUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		//les roles de spring security sont de type GrantedAuthority:
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoles().forEach(r ->{
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
		//classe User de spring Security par défaut:
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
