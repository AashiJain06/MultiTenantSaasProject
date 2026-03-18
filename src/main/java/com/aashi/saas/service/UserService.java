package com.aashi.saas.service;

import java.util.List;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.UserRequestDto;
import com.aashi.saas.dto.UserResponseDto;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.entity.User;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.repository.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserService {

   private UserRepository userRepository;

   private TenantRepository tenantRepository;
  
   private PasswordEncoder passwordEncoder;
	
   private EntityManager entityManager;
public UserResponseDto createUser(UserRequestDto userDto)
{
	Long tenantId = TenantContext.getTenantId();

    Tenant tenant = tenantRepository.findById(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));
    User user = new User();
    user.setTenant(tenant);
    user.setUsername(userDto.getUsername());
    user.setRole(userDto.getRole());
    user.setEmail(userDto.getEmail());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User savedUser =  userRepository.save(user);
    return new UserResponseDto(savedUser.getId(),savedUser.getUsername(),savedUser.getEmail(),savedUser.getRole());
}
public UserResponseDto getUserById(Long id)
{
	User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    return new UserResponseDto(user.getId(),user.getUsername(), user.getEmail(), user.getRole());
}
@Transactional
public List<User> getAllusers()
{
	Session session = entityManager.unwrap(Session.class);

    session.enableFilter("tenantFilter")
      .setParameter("tenantId", TenantContext.getTenantId());
    System.out.println("Tenant ID: " + TenantContext.getTenantId());
	return userRepository.findAll();
}
public void deleteUser(Long id)
{
	userRepository.deleteById(id);
}
public List<UserResponseDto> getUserByTenant()
{
	Long tenantId = TenantContext.getTenantId();
	List<User> users = userRepository.findAll();
	return users.stream().map(user ->new UserResponseDto(user.getId(),user.getUsername(),user.getEmail(),user.getRole())).toList();
}
}
