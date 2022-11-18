package annotationWithoutXml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("annotationWithoutXml")
@PropertySource("classpath:cricket.properties")
public class Config {

	@Bean
	public Fortune sadFortune() {
		return new SadFortune();
	}
	
	@Bean
	public Coach swimCoach() {
		return new SwimCoach(sadFortune());
	}
}
