package annotationWithoutXml;

import org.springframework.stereotype.Component;

@Component
public class HappyFortune implements Fortune {

	public String getFortune() {
		// TODO Auto-generated method stub
		return "Entered Happy Fortune";
	}

}
