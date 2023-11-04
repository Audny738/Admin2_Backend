package com.gate_software.ams_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmsBackendApplication.class, args);
	}

}
