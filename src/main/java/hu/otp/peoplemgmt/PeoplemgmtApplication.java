package hu.otp.peoplemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PeoplemgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeoplemgmtApplication.class, args);
	}

}
