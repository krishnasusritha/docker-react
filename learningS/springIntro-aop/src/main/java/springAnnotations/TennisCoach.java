package springAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach{
	
	private Fortune fortune;
	
	@Value("${name}")
	private String name;
	
	@Value("${email}")
	private String email;

	// If it has a single implementation and autowired is commented out then also it works
	@Autowired
	public TennisCoach(@Qualifier("sadFortune") Fortune fortune) {
		super();
		this.fortune = fortune;
	}



	public String getDailyWorkout() {
		// TODO Auto-generated method stub
		return "Entered Tennis Coach " + name + " " + email;
	}
	
	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return fortune.getFortune();
	}
	
	// these annotations are removed in the java library should add explicit library(javax-annotation-api) for this
//	@PostConstruct
//	public void beforeInit() {
//		System.out.println("Entered init method");
//	}
	
//	@PreDestory
//	public void beforeDestroy() {
//		System.out.println("Entered destory method");
//	}

}
