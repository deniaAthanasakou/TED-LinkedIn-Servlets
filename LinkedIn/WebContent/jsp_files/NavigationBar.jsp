<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/navbar.css" type="text/css">
		
		<!-- Add icon library -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<title>Navigation Bar</title>
	</head>
	<body>
		<div class="icon-bar">
  			<div class="item home" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/home.jsp'" >
  				<i class="glyphicon glyphicon-home"></i>
  				<p class="caption">Home</p>
  			</div>
			<div class="item network" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/network.jsp'">
  				<i class="glyphicon glyphicon-globe"></i>
  				<p class="caption">Network</p>
	  		</div>
			<div class="item jobs" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/jobs.jsp'">
  				<i class="glyphicon glyphicon-briefcase"></i>
  				<p class="caption">Jobs</p>
	  		</div>
			<div class="item messaging" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/messaging.jsp'">
  				<i class="glyphicon glyphicon-envelope"></i>
  				<p class="caption">Messaging</p>
	  		</div>
			<div class="item notifications" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/notifications.jsp'">
  				<i class="glyphicon glyphicon-bell"></i>
  				<p class="caption">Notifications</p>
	  		</div>
			<div class="item profile" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/profile.jsp'">
  				<i class="glyphicon glyphicon-user"></i>
  				<p class="caption">Profile</p>
	  		</div>
			<div class="item settings" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/settings.jsp'">
  				<i class="glyphicon glyphicon-cog"></i>
  				<p class="caption">Settings</p>
	  		</div>
	  		<div class="item logout" onclick="document.getElementById('logoutFrom').submit()">
  				<i class="glyphicon glyphicon-log-out"></i>
  				<p class="caption">Logout</p>
		  	</div>
	  		<form id="logoutFrom" action="${pageContext.request.contextPath}/Logout" method="post">	
		  	</form>
		</div>
	</body>
</html>