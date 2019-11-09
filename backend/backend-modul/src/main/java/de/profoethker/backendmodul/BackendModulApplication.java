package de.profoethker.backendmodul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
	                registry.addMapping("/api/*").allowedOrigins("http://127.0.0.1:8000");
	            }
	        };
	    }
	
	public static void main(String[] args) {
		SpringApplication.run(BackendModulApplication.class, args);
	}

}
