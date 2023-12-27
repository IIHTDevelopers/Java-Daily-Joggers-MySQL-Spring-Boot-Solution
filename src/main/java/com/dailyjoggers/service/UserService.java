package com.dailyjoggers.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailyjoggers.dto.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);

	Page<UserDTO> getAllUsers(Pageable pageable);

	UserDTO getUserById(Long userId);

	UserDTO updateUserById(Long userId, UserDTO userDTO);

	void deleteUserById(Long userId);
}
