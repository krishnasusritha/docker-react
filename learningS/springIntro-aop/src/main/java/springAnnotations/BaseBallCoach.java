package springAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BaseBallCoach implements Coach{
	
	@Autowired
	@Qualifier("happyFortune")
	private Fortune fortune;

	public String getDailyWorkout() {
		// TODO Auto-generated method stub
		return "Entered BaseBall Coach";
	}

	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return fortune.getFortune();
	}

}
