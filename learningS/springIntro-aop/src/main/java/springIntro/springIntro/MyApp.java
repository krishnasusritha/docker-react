package springIntro.springIntro;

public class MyApp {

	public static void main(String[] args) {
		Coach c = new BaseBallCoach(new Fortune() {
			
			public String getFortune() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		System.out.println(c.getDailyWorkOut());

	}

}
