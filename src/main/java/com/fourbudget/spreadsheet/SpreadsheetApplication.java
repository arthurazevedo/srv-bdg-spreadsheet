package com.fourbudget.spreadsheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication //, exclude = JpaRepositoriesAutoConfiguration.class)
@EnableJpaAuditing
public class SpreadsheetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpreadsheetApplication.class, args);
	}

}
