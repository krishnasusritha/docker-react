package springAnnotations;

import org.springframework.stereotype.Component;

@Component
public class SadFortune implements Fortune {

	public String getFortune() {
		// TODO Auto-generated method stub
		return "Entered Sad Fortune";
	}

}
