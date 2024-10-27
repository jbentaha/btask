package com.btask.token;

import com.btask.StringUtil;
import com.btask.user.BUser;
import com.btask.user.BUserDetails;
import com.btask.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtTokenUtil {

	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(@NotNull final UserDetails userDetails) {
		final Map<String, Object> claims = new HashMap<>();
		setClaims(claims, userDetails);
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private void setClaims(Map<String, Object> claims, UserDetails userDetails) {
		BUser user = ((BUserDetails) userDetails).user();
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		claims.put("lastName", user.getLastName());

		StringBuilder sb = new StringBuilder();
		List<UserRole> roles = user.getRoles();
		int size = roles.size();

		for (int i = 0; i < size; i++) {
			sb.append(roles.get(i));
			if (i < size - 1) {
				sb.append(StringUtil.COMMA);
			}
		}

		claims.put("roles", sb);
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
