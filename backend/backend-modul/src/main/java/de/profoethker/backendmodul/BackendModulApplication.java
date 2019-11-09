package de.profoethker.backendmodul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Core server
 * 
 * @author miguel
 *
 */
@SpringBootApplication
public class BackendModulApplication {

	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
						.allowedHeaders("Access-Control-Allow-Origin", "*");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendModulApplication.class, args);
	}

}
