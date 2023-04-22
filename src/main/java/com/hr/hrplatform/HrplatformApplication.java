package com.hr.hrplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class HrplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrplatformApplication.class, args);
	}

}
