package annotationWithoutXml;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotateApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		//setter or method injection
		Coach coach2 = context.getBean("footBallCoach", Coach.class);
		
		System.out.println(coach2.getDailyWorkout());
		
		System.out.println(coach2.getDailyFortune());
		
		Coach coach3 = context.getBean("swimCoach", Coach.class);
		
		System.out.println(coach3.getDailyWorkout());
		
		System.out.println(coach3.getDailyFortune());
				
		context.close();
	}
}
