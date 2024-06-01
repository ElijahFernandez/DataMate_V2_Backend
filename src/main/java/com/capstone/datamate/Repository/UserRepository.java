package com.capstone.datamate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.datamate.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	UserEntity findByUsername(String username);
	UserEntity findByEmail(String email);
}
