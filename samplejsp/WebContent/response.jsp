<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String place=request.getParameter("Place");

Cookie ck=new Cookie("myApp.Place",place);

ck.setMaxAge(60*60);

response.addCookie(ck);
%>

Set place to: ${param.Place}
<br>
<a href="homecookies.jsp">Return to home</a>

</body>
</html>