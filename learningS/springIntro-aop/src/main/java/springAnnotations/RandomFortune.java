package springAnnotations;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RandomFortune implements Fortune {

	@Value("${dataArray}")
	private String[] data;

	private Random random = new Random();
	public String getFortune() {
		// TODO Auto-generated method stub
		return data[random.nextInt(data.length)];
	}


}
