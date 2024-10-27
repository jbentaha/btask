package com.btask.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BUserDetailsService implements UserDetailsService {

	public static final String USER_NOT_FOUND = "User not found with email: ";

	private final BUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		BUser user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));

		return new BUserDetails(user);
	}

}

