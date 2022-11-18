package login;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Process extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		String name = request.getParameter("uname");
		String password = request.getParameter("password");
		if (name.equals("admin@gmail.com") && password.equalsIgnoreCase("123456")) {
			response.sendRedirect("success.html");
		} else
			response.sendRedirect("error.html");
	}

}
