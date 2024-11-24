package hu.otp.peoplemgmt.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    private static final MSSQLServerContainer<?> SQLSERVER_CONTAINER =
            new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2019-latest")
                    .acceptLicense();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", SQLSERVER_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", SQLSERVER_CONTAINER::getUsername);
        registry.add("spring.datasource.password", SQLSERVER_CONTAINER::getPassword);
    }

    static {
        SQLSERVER_CONTAINER.start();
    }
}
