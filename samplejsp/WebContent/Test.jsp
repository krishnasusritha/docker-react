<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

Request User Agent :<%= request.getHeader("User-Agent") %>

<br/>

Request User Language:<%= request.getLocale() %>

</body>
</html>