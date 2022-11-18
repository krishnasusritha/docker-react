package annotationWithoutXml;

import org.springframework.beans.factory.annotation.Value;

public class SwimCoach implements Coach{

	private Fortune fortune;
	
	@Value("${name}")
	private String name;
	
	@Value("${email}")
	private String email;
	
	public SwimCoach(Fortune fortune) {
		super();
		this.fortune = fortune;
	}

	public String getDailyWorkout() {
		// TODO Auto-generated method stub
		return "Entered Swim Coach " + name + " " + email;
	}

	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return fortune.getFortune();
	}

}
