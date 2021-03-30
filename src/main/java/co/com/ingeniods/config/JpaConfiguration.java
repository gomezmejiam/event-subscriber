package co.com.ingeniods.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("co.com.ingeniods.humantalent.infrastructure.repository.port")
public class JpaConfiguration {

}
