<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="../css_files/navbar.css">
		<!-- Add icon library -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<title>Navigation Bar</title>
	</head>
	<body>
		<div class="icon-bar">
  			<a class="home" href="./home.jsp">
	  			<div class="item">
	  				<i class="glyphicon glyphicon-home"></i>
	  				<p class="caption">Αρχική Σελίδα</p>
	  			</div>
  			</a>
			<a class="network" href="./network.jsp">
				<div class="item">
	  				<i class="glyphicon glyphicon-globe"></i>
	  				<p class="caption">Δίκτυο</p>
		  		</div>
	  		</a> 
			<a class="jobs">
				<div class="item">
	  				<i class="glyphicon glyphicon-briefcase"></i>
	  				<p class="caption">Αγγελίες</p>
		  		</div>
	  		</a>
			<a class="messaging">
				<div class="item">
	  				<i class="glyphicon glyphicon-envelope"></i>
	  				<p class="caption">Συζητήσεις</p>
		  		</div>
	  		</a>
			<a class="notifications">
				<div class="item">
	  				<i class="glyphicon glyphicon-bell"></i>
	  				<p class="caption">Ειδοποιήσεις</p>
		  		</div>
	  		</a> 
			<a class="profile" href="./profile.jsp">
				<div class="item">
	  				<i class="glyphicon glyphicon-user"></i>
	  				<p class="caption">Προσωπικά Στοιχεία</p>
		  		</div>
	  		</a> 
			<a class="settings">
				<div class="item">
	  				<i class="glyphicon glyphicon-cog"></i>
	  				<p class="caption">Ρυθμίσεις</p>
		  		</div>
	  		</a>
		</div>
	</body>
</html>