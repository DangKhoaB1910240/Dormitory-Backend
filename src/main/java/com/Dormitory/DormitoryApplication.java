package com.Dormitory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DormitoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DormitoryApplication.class, args);
	}

}
