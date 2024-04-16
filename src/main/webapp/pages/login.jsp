<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/FirstProject/stylesheets/login.css" />
</head>
<body>
	<div class="login-box">
	<%--Display error message if it exists --%>
	<%
	String errorMessage = (String) request.getAttribute("errorMessage");
	if(errorMessage != null && !errorMessage.isEmpty()){
	
	%>
	
	<p class="error-message"> <%=errorMessage%></p>
	<%
	}
	%>
		<h2>Login</h2>
		<form action="/FirstProject/LoginServlet" method="post">
			<div class="row">
				<div class="col">
					<label for="username">Username:</label> 
					<input type="text" id="username" name="username" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="password">Password:</label> 
					<input type="password" id="password" name="password" required>
				</div>
			</div>
			<button type="submit" class="login-button" value="Login">Login</button>
		</form>
	</div>
</body>
</html>