package com.aashi.saas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.aashi.saas.dto.UserRequestDto;
import com.aashi.saas.dto.UserResponseDto;
import com.aashi.saas.entity.User;
import com.aashi.saas.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name="bearerAuth")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public UserResponseDto createUser(@RequestBody  UserRequestDto userRequestDto)
	{
	 return userService.createUser(userRequestDto);
	}
   @PreAuthorize("hasRole('ADMIN')")
   @GetMapping("/get")
   public Page<UserResponseDto> getAlluser(Pageable pageable)
   {
	   return userService.getAllusers(pageable);
   }
   
   @GetMapping("/{id}")
   public UserResponseDto getUserById(@PathVariable Long id)
   {
	   return userService.getUserById(id);
   }
   @DeleteMapping("/{id}")
   public String deleteUser(@PathVariable Long id) {
       userService.deleteUser(id);
       return "User deleted successfully";
   }
   
}
