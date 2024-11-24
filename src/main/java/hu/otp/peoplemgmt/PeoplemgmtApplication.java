package hu.otp.peoplemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry point for the People Management application.
 */
@SpringBootApplication
@EnableJpaAuditing
public class PeoplemgmtApplication {

	/**
	 * The main method that starts the People Management application.
	 * @param args command-line arguments passed during the application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(PeoplemgmtApplication.class, args);
	}

}
