package com.marcello.agendamento_aula.service.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.marcello.agendamento_aula.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenService {
	
	@Value("${jwt.expiration}")
	public long EXPIRATION;
	
	@Value("${jwt.secret}")
	public String SECRET_KEY;

	@Value("${jwt.auth.key}")
	public String AUTH_KEY;

	public String generateToken(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + this.EXPIRATION);
		
		return Jwts.builder()
				.setIssuer(usuario.getNome())
				.setSubject(usuario.getId().toString())
				// .claim(AUTH_KEY, usuario.getPerfil().toString())
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, this.SECRET_KEY)
				.compact();
	}
	
	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.SECRET_KEY).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
}
