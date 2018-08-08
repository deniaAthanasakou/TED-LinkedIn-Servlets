<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- custom -->
		<link rel="stylesheet" href="../css_files/main_css.css">
		<link rel="stylesheet" href="../css_files/welcome_page.css">
		
		<title>TestRegister</title>
	</head>
	<body>
		<jsp:include page="./Header.jsp" />
	
		<div class="main">
			<div class="container">
				<h2>Register: <%=request.getAttribute("register") %></h2>
			</div>
		</div>
		
		<jsp:include page="./Footer.jsp" />
	</body>
</html>