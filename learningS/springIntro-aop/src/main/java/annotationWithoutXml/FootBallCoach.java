package annotationWithoutXml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FootBallCoach implements Coach{
	
	private Fortune fortune;

	public String getDailyWorkout() {
		// TODO Auto-generated method stub
		return "Entered FootBall Coach";
	}

	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return fortune.getFortune();
	}

	public Fortune getFortune() {
		return fortune;
	}

	//If autowired is present methodname can be anything.
//	@Autowired
//	public void setFortune(Fortune fortune) {
//		this.fortune = fortune;
//	}
	
	@Autowired
	@Qualifier("happyFortune")
	public void doSomething(Fortune fortune) {
		this.fortune = fortune;
	}
	
	

}
