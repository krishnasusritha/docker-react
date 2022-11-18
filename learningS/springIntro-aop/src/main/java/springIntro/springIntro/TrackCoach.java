package springIntro.springIntro;

public class TrackCoach  implements Coach{

	private Fortune fortune;
	
	public TrackCoach(Fortune fortune) {
		this.fortune = fortune;
	}

	public TrackCoach() {
		super();
	}

	public Fortune getFortune() {
		return fortune;
	}

	public void setFortune(Fortune fortune) {
		this.fortune = fortune;
	}

	public String getDailyWorkOut() {
		// TODO Auto-generated method stub
		return " In TrackCoach";
	}

	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return "Fortune in TRack Coach" + fortune.getFortune();
	}


}
