<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/post_item.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/user_home.css" type="text/css">
		<title>Post</title>
		
		
	</head>
	<body>
		<c:import url="Header.jsp" /> 
		
		<div class="main">
			<div class="myContainer">	
				
				<c:set var="post" value="${post}" scope="request"/>
				<c:import url="PostItemForNotifications.jsp"/>
			</div>
		</div>
		
		<c:import url="Footer.jsp" />

	</body>
	</html>