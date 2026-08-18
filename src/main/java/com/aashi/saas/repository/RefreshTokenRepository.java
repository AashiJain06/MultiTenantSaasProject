package com.aashi.saas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.RefreshToken;



public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	void deleteByUser_Id(Long userId);
}
