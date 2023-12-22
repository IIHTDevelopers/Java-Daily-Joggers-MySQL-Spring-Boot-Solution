package com.dailyjoggers.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dailyjoggers.dto.UserDTO;
import com.dailyjoggers.entity.User;
import com.dailyjoggers.exception.ResourceNotFoundException;
import com.dailyjoggers.repo.UserRepository;
import com.dailyjoggers.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		User savedUser = userRepository.save(user);

		UserDTO savedUserDTO = new UserDTO();
		BeanUtils.copyProperties(savedUser, savedUserDTO);
		return savedUserDTO;
	}

	@Override
	public Page<UserDTO> getAllUsers(Pageable pageable) {
		Page<User> usersPage = userRepository.findAllByOrderByUsernameAsc(pageable);

		List<UserDTO> userDTOList = usersPage.getContent().stream().map(user -> {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(user, userDTO);
			return userDTO;
		}).collect(Collectors.toList());

		return new PageImpl<>(userDTOList, pageable, usersPage.getTotalElements());
	}

	@Override
	public UserDTO getUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		return userDTO;
	}

	@Override
	public UserDTO updateUserById(Long userId, UserDTO userDTO) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			BeanUtils.copyProperties(userDTO, user);
			User updatedUser = userRepository.save(user);

			UserDTO updatedUserDTO = new UserDTO();
			BeanUtils.copyProperties(updatedUser, updatedUserDTO);
			return updatedUserDTO;
		} else {
			throw new ResourceNotFoundException("User not found with id: " + userId);
		}
	}

	@Override
	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
	}
}
