package org.sid.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.bytebuddy.asm.MemberSubstitution.Substitution.Chain;

public class JWTAuthorisationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(SecurityConstant.HEADER_STRING);
		if (jwt == null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)) {
			//pass to the next filter security filter: 
			filterChain.doFilter(request, response);
			return ;
		}
		//signer le JWT + supprim le prefix(bearer):
		Claims claims  = Jwts.parser()
				.setSigningKey(SecurityConstant.SECRET)
				.parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX, ""))
				.getBody();
		
		//username du user
		String username = claims.getSubject();
		ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
		//les roles doit sous forme Collection de GrantedAuthority :
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(r ->{
			authorities.add(new SimpleGrantedAuthority(r.get("authority")));//name key = authority
		});
		//user deja authentifié :
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
		//charger dans le contexte de sécurité :
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

}
