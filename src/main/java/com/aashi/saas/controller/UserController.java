package com.aashi.saas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.dto.UserDto;
import com.aashi.saas.entity.User;
import com.aashi.saas.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public UserDto createUser(@RequestBody User user)
	{
	 return userService.createUser(user);
	}
   @GetMapping
   public List<User> getAlluser()
   {
	   return userService.getAllusers();
   }
   
   @GetMapping("/{id}")
   public UserDto getUserById(@PathVariable Long id)
   {
	   return userService.getUserById(id);
   }
   @DeleteMapping("/{id}")
   public String deleteUser(@PathVariable Long id) {
       userService.deleteUser(id);
       return "User deleted successfully";
   }
   @GetMapping("/tenant")
   public List<UserDto> getUserByTenant()
   {
	   return userService.getUserByTenant();
   }
}
