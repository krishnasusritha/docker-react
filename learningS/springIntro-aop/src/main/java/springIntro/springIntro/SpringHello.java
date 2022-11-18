package springIntro.springIntro;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringHello {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		Coach coach = context.getBean("myCoach", Coach.class);
		
		System.out.println(coach.getDailyWorkOut());
		System.out.println(coach.getDailyFortune());
		
		CricketCoach ccoach = context.getBean("myCoach", CricketCoach.class);
		
		System.out.println(ccoach.getDailyWorkOut());
		System.out.println(ccoach.getDailyFortune() + " " + ccoach.getEmail() + " " + ccoach.getName());
		context.close();
	}

}
