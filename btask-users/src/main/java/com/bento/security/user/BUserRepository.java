package com.bento.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BUserRepository extends JpaRepository<BUser, Long> {

	Optional<BUser> findByEmail(String email);

}
