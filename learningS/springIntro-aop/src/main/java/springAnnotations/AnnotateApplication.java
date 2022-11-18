package springAnnotations;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotateApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("annotationContext.xml");
		
		//constructor injection
		Coach coach = context.getBean("tennisCoach", Coach.class);
		
		System.out.println(coach.getDailyWorkout());
		
		System.out.println(coach.getDailyFortune());
		
		Coach tennis = context.getBean("tennisCoach", Coach.class);
		
		// check for singleton scope
		System.out.println("For singleton both the objects are referred to same location, so it should be true = " + (coach==tennis));
		
		System.out.println(coach);
		
		System.out.println(tennis);
		
		//setter or method injection
		Coach coach2 = context.getBean("footBallCoach", Coach.class);
		
		System.out.println(coach2.getDailyWorkout());
		
		System.out.println(coach2.getDailyFortune());
		
		Coach football = context.getBean("footBallCoach", Coach.class);
		
		// check for prototype scope
		System.out.println("For prototype both the objects are not referred to same location, so it should be false = " + (coach2==football));
		
		System.out.println(coach2);
		
		System.out.println(football);
		
		// field injection
		Coach coach3 = context.getBean("baseBallCoach", Coach.class);
		
		System.out.println(coach3.getDailyWorkout());
		
		System.out.println(coach3.getDailyFortune());
		
		context.close();
	}
}
