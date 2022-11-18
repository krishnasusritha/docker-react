<%@  import="com.jspsample.*%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<p>Calling Java file from jsp:</p>
<%= sample.makeItLower("My first java linked program") %>
</body>
</html>