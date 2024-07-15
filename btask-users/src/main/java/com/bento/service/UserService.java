package com.bento.service;

import com.bento.dao.UserRepository;
import com.bento.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public User getUserById(final Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void createUser(final User user) {
		userRepository.save(user);
	}

	public void deleteUser(final Long id) {
		userRepository.deleteById(id);
	}

}
