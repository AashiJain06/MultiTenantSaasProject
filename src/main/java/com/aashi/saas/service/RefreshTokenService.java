package com.aashi.saas.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aashi.saas.entity.RefreshToken;
import com.aashi.saas.entity.User;
import com.aashi.saas.repository.RefreshTokenRepository;
import com.aashi.saas.repository.UserRepository;

@Service
public class RefreshTokenService {
	
	private final RefreshTokenRepository repository;
    private final UserRepository userRepository;
	public RefreshTokenService(RefreshTokenRepository repository , UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository; ;
	}
	
	public RefreshToken createRefreshToken(Long userId) {

        RefreshToken token = new RefreshToken();
        User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        token.setUser(user);
        
        token.setToken(UUID.randomUUID().toString());

        token.setExpiryDate(
                Instant.now().plus(7, ChronoUnit.DAYS));

        return repository.save(token);
    }
	
	public RefreshToken verify(String token){

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Refresh Token"));

        if(refreshToken.getExpiryDate().isBefore(Instant.now())){

            repository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }

        return refreshToken;
	}
	public void deleteByUser(Long userId) {
		
		repository.deleteByUser_Id(userId);
	}
}
