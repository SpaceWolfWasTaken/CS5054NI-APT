<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
	</head>
	<body>
	<!-- 
		<sql:setDataSource var="dbConnection" driver="com.mysql.jdbc.Driver"
			url="jdbc:mysql://localhost:3306/college_app" user="root"
			password="" />
	
		<sql:query var="students" dataSource="${dbConnection}">
		SELECT first_name, last_name, user_name FROM tut_three WHERE user_name LIKE "%a%"
		</sql:query>
	-->
		<div class="students-info">
			<div class="users">
				<c:forEach var="student" items="${studentList}">
					<div class="card">
						<%-- <img src="http://localhost:8080/images/${student.image}"
							class="card-img-top" alt="..."> --%>
						<div class="card-body">
							<h4 class="card-title">${student.firstName} <!-- Uses Reflection. Basically as long as you follow java getter pattern
							and naming convention, jsp auto uses the getter here -->
								${student.lastName}</h4>
							<h5 class="card-text">${student.subject}</h5>
						</div>
						
						<form method="post"
							action="${pageContext.request.contextPath}/ModifyServlet">
							<input type="hidden" name="updateId" value="${student.username}" />
							<button type="submit">Update</button>
						</form>
						<form id="deleteForm-${student.username}" method="post"
							action="${pageContext.request.contextPath}/ModifyServlet">
							<input type="hidden" name="deleteId" value="${student.username}" />
							<button type="button"
								onclick="confirmDelete('${student.username}')">Delete</button>
						</form>
					</div>
					
				</c:forEach>
			</div>
		</div>
	</body>
</html>