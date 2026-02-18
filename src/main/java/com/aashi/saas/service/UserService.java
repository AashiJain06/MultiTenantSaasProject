package com.aashi.saas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
public User createUser(User user)
{
	Long tenantId = user.getTenant().getId();

    Tenant tenant = tenantRepository.findById(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));

    user.setTenant(tenant);

    return userRepository.save(user);
}
public User getUserById(Long id)
{
	return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
}
public List<User> getAllusers()
{
	return userRepository.findAll();
}
public void deleteUser(Long id)
{
	userRepository.deleteById(id);
}
public List<User> getUserByTenant(Long tenantId)
{
	List<User> users = userRepository.findByTenantId(tenantId);
	return users;
}
}
