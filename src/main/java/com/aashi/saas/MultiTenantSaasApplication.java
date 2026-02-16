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
	

}
