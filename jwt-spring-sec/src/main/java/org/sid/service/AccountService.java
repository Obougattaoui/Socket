package org.sid.service;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.springframework.stereotype.Service;


public interface AccountService {
	public AppUser saveUser(AppUser user);//ajouter un user
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username, String roleName);
	public AppUser findUserByUsername(String username);
}
