<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
The Student is confirmed : ${param.fname} ${param.lname}
<br>
The Student Country is :${param.country}
<br>
Student gender is: ${param.Gender} 
<br>

Languages known to Student are:
<ul>
<%
String[] lang=request.getParameterValues("language");
for(String value: lang){
out.println("<li>"+value+"</li> ");
}
%>
</ul>
</body>
</html>