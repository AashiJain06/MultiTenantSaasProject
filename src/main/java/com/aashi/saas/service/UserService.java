package com.aashi.saas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.UserDto;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.entity.User;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.repository.UserRepository;

@Service
public class UserService {
	@Autowired
   private UserRepository userRepository;
	@Autowired
   private TenantRepository tenantRepository;
	
public UserDto createUser(User user)
{
	Long tenantId = user.getTenant().getId();

    Tenant tenant = tenantRepository.findById(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));

    user.setTenant(tenant);

    User savedUser =  userRepository.save(user);
    return new UserDto(savedUser.getId(), savedUser.getUsername(),savedUser.getEmail(),savedUser.getRole());
}
public UserDto getUserById(Long id)
{
	User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
}
public List<User> getAllusers()
{
	return userRepository.findAll();
}
public void deleteUser(Long id)
{
	userRepository.deleteById(id);
}
public List<UserDto> getUserByTenant()
{
	Long tenantId = TenantContext.getTenantId();
	List<User> users = userRepository.findByTenantId(tenantId);
	return users.stream().map(user ->new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getRole())).toList();
}
}
