<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p>Hello World. The time on the server is <%=new java.util.Date() %></p>
<p><%= new String("Hello World").toUpperCase() %>
  <p> <%=2*3 %></p>
    <p> <%=2>3 %></p>
    <%!
    	String makeItLower(String data)
    	{
    	return data.toLowerCase();
    	}
    
    %>
    <%
    for(int i=0;i<10;i++)
    {
    	out.print(makeItLower("Hello WOrld")+i);
    }
    
    %>
  
  </body>
</body>
</html>