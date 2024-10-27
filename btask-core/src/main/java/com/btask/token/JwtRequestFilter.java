package com.btask.token;

import com.btask.StringUtil;
import com.btask.user.BUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final String HEADER_REQUEST_GATEWAY = "X-Request-Gateway";

	private static final String ROUTE_GATEWAY_TO = "route-gateway-to-";

	private static final String AUTHORIZATION = "Authorization";

	private static final String BEARER_ = "Bearer ";

	private final BUserDetailsService userDetailsService;

	private final JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader(AUTHORIZATION);

		if(!isRequestFromGateway(request)) {
			chain.doFilter(request, response);
		}

		String email = null;
		String jwt = null;

		if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_)) {
			jwt = authorizationHeader.substring(7);
			email = jwtTokenUtil.getUsernameFromToken(jwt);
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			if (jwtTokenUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		chain.doFilter(request, response);
	}

	private boolean isRequestFromGateway(HttpServletRequest request) {

		final String gateway = request.getHeader(HEADER_REQUEST_GATEWAY);

		String service = getServiceName(request.getRequestURI());

 		return service != null && (ROUTE_GATEWAY_TO + service).equals(gateway);
	}

	private String getServiceName(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}

		String[] segments = path.split(StringUtil.SLASH);

		return Arrays.stream(segments).filter(segment -> !segment.isEmpty()).findFirst().orElse(null);
	}

}

