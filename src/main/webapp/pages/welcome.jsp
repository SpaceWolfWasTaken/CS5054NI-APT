<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheets/welcome.css" />
</head>
<body>
	<%
		String userSession = (String) session.getAttribute("username");
		long sessionTime = (long) session.getAttribute("creation");
		String sess = sessionTime + "";
		String cookieUsername  = null;
		String cookieSessionID = null;
		String cookieHello = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie: cookies){
				if(cookie.getName().equals("user")) cookieUsername = cookie.getValue();
				if(cookie.getName().equals("JSESSIONID")) cookieSessionID = cookie.getValue();
				if(cookie.getName().equals("hello")) cookieHello = cookie.getValue();
			}
		}
	%>
	
	<jsp:include page="header.jsp"></jsp:include>

	<div class="welcome-container">
		<h1>Hello <%=cookieUsername %>. Welcome to our page!</h1>
		<h3>Cookie session Id is <%=cookieSessionID %></h3>
		<p>Session username: <%=userSession %></p>
		<p>Session creation time: <%=sess %></p>
		<p> Cookie message: <%=cookieHello %></p>
		<a href="${pageContext.request.contextPath}/pages/login.jsp">
			<button class="home-button">Continue to Home Page</button>
		</a>
	</div>
	
	<jsp:include page="footer.jsp"></jsp:include>
	
</body>
</html>