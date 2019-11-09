package de.profoethker.backendmodul;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author miguel
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "de.profoethker.backendmodul", entityManagerFactoryRef = "entityManagerFactory")
public class DBConfig {

	@Bean
	public DataSource datasource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.sqlite.JDBC");
		dataSourceBuilder.url("jdbc:sqlite:quiz.sqlite");
		return dataSourceBuilder.build();
	}

}
