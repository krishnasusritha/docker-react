package jackson.json.demo;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {

	public static void main(String[] args) {
		try {

			ObjectMapper mapper = new ObjectMapper();

			Student s = mapper.readValue(new File("data/student.json"), Student.class);

			System.out.println(s.toString());
			System.out.println("ID is " + s.getAddress());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
