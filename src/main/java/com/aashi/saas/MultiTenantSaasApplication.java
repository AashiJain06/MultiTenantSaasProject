package com.aashi.saas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.aashi.saas.entity.Tenant;
import com.aashi.saas.entity.User;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.repository.UserRepository;

@SpringBootApplication
public class MultiTenantSaasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiTenantSaasApplication.class, args);
	}
	@Bean
	CommandLineRunner init(TenantRepository tenantRepo, UserRepository userRepo) {
	    return args -> {

	        Tenant tenant = new Tenant();
	        tenant.setName("CompanyA");
	        tenantRepo.save(tenant);

	        User user = new User();
	        user.setUsername("admin");
	        user.setEmail("admin@companya.com");
	        user.setPassword("test");
	        user.setRole("ADMIN");
	        user.setTenant(tenant);
	        userRepo.save(user);

	        System.out.println("Test data inserted.");
	    };
	}


}
