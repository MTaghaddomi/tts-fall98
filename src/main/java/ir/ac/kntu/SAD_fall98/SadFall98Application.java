package ir.ac.kntu.SAD_fall98;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class SadFall98Application {

	public static void main(String[] args) {
		SpringApplication.run(SadFall98Application.class, args);
	}

}
