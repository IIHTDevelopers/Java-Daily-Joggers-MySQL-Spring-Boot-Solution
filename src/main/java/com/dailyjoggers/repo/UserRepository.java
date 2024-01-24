package com.dailyjoggers.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailyjoggers.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAllByOrderByUsernameAsc();
}
