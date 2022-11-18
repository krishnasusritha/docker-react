package springIntro.springIntro;

public class BaseBallCoach implements Coach{

	public Fortune getFortune() {
		return fortune;
	}
	public void setFortune(Fortune fortune) {
		this.fortune = fortune;
	}

	private Fortune fortune;
	
	public BaseBallCoach(Fortune f) {
		fortune = f;
	}
	public String getDailyWorkOut() {
		// TODO Auto-generated method stub
		return " In BaseBallCoach";
	}

	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return "Fortune in Base Ball coach " + fortune.getFortune();
	}

}
