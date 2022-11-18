package springIntro.springIntro;

public class CricketCoach implements Coach{

	private Fortune fortune;
	
	private String email;
	
	private String name;
	
	private static int i=0;
	
	private CricketCoach() {
		i++;
	}
	
	public String getDailyWorkOut() {
		// TODO Auto-generated method stub
		return "Entered Cricket Coach " + i;
	}

	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return "Fortune in Cricket Coach " + fortune.getFortune() + i;
	}

	public Fortune getFortune() {
		return fortune;
	}

	public void setFortune(Fortune fortune) {
		this.fortune = fortune;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// adding init method
	public void doInit() {
		System.out.println("entered init method");
	}
	
	//adding destroy method. Custom destroy method doesn't work for prototype beans. YOu should use Disposable bean interface for cleanup
	public void doDestroy() {
		System.out.println("entered destroy method");
	}
}
