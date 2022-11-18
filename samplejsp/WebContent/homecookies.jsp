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
String Place="Hyderabad";

Cookie[] c= request.getCookies();

if(c!=null)
{
	for(Cookie temp: c)
	{
		if("myApp.Place".equals(temp.getName()))
		{
			Place=temp.getValue();
			break;
		}
	}
}


%>

<h4>Places to visit <%=Place %></h4>
<ul>
<li>jdjsdijfh</li>
<li>asfkjgk</li>
<li>sfnkjgeg</li>
<li>ajajkh</li>
</ul>


<a href="cookies.html">Personalize this page</a>
</body>
</html>