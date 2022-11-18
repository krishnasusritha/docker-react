package aopdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("aopdemo")
@EnableAspectJAutoProxy
public class Config {
	
	@Bean
	public Account account() {
		return new Account("abc", "123");
	}

}

