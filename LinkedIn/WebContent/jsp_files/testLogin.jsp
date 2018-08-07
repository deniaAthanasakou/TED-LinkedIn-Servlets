<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- custom -->
		<link rel="stylesheet" href="../css_files/main_css.css">
		<link rel="stylesheet" href="../css_files/welcome_page.css">
		
		<title>TestLogin</title>
	</head>
	<body>
		<jsp:include page="./Header.jsp" />
	
		<div class="main">
			<div class="container">
				<h2>Changed <%=request.getAttribute("changed") %></h2>
				<h2>city <%=request.getAttribute("changed1") %></h2>
				<h2>city <%=request.getAttribute("changed2") %></h2>
			</div>
		</div>
		
		<jsp:include page="./Footer.jsp" />
	</body>
</html>