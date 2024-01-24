package com.dailyjoggers.service;

import java.util.List;

import com.dailyjoggers.dto.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);

	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long userId);

	UserDTO updateUserById(Long userId, UserDTO userDTO);

	boolean deleteUserById(Long userId);
}
