package com.bento.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(@NotNull final UserDetails userDetails) {
		final Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(final Map<String, Object> claims, final String subject) {
		final byte[] decodedSecret = Base64.getDecoder().decode(secret);
		final SecretKey secretKey = Keys.hmacShaKeyFor(decodedSecret);

		return Jwts.builder()//
				.setClaims(claims)//
				.setSubject(subject)//
				.setIssuedAt(new Date(System.currentTimeMillis()))//
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))//
				.signWith(secretKey, SignatureAlgorithm.HS512)//
				.compact();
	}

	public String getUsernameFromToken(final String token) {
		final byte[] decodedSecret = Base64.getDecoder().decode(secret);
		final SecretKey secretKey = Keys.hmacShaKeyFor(decodedSecret);

		final Claims claims = Jwts.parserBuilder()//
				.setSigningKey(secretKey)//
				.build()//
				.parseClaimsJws(token)//
				.getBody();

		return claims.getSubject();
	}

	public boolean validateToken(final String token, final UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(final String token) {
		final byte[] decodedSecret = Base64.getDecoder().decode(secret);
		final SecretKey secretKey = Keys.hmacShaKeyFor(decodedSecret);

		final Date expiration = Jwts.parserBuilder()//
				.setSigningKey(secretKey)//
				.build()//
				.parseClaimsJws(token)//
				.getBody()//
				.getExpiration();

		return expiration.before(new Date());
	}

}
