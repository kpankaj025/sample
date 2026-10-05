package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;


@Configuration
public class ProjectConfig {
	
	@Bean
    public RestClient restClient() {
        // Create the RestClient directly using the static builder method
        return RestClient.builder()
                // .baseUrl("https://example.com") // optional configuration
                .build();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
}
