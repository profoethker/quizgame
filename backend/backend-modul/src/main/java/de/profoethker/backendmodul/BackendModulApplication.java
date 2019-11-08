package de.profoethker.backendmodul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Core server
 * @author miguel
 *
 */
@SpringBootApplication
@EnableWebMvc
public class BackendModulApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendModulApplication.class, args);
	}

}
