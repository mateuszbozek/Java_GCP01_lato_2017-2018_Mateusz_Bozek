package com.lenda.marc;

import com.lenda.marc.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import(value = {AppConfig.class})
@EnableJpaRepositories(basePackages = { "com.lenda.marc.repository" })
public class LibraryOnlineApplication {


	public static void main(String[] args) {
		SpringApplication.run(LibraryOnlineApplication.class, args);
	}
}
