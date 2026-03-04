package com.aashi.saas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.service.UserService;

@Controller
public class ViewController {
	@Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getUsers(@RequestParam Long tenantId, Model model) {

        TenantContext.setTenantId(tenantId);

        model.addAttribute("users", userService.getUserByTenant());

        return "users/userlist";
        
    }
}
