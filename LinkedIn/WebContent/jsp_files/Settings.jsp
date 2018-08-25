<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/settings.css" type="text/css">
		
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Settings</title>
	</head>
	<body>
	
	<jsp:include page="Header.jsp" /> 
	
	<div class="main">
			<div class="container">
			
				
				<div class="settingsBox">
					<h3>Αλλάξτε το email και τον κωδικό πρόσβασης σας</h3>
					<br><br>
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/Settings" accept-charset="UTF-8" enctype="multipart/form-data">
						
						<% if ( request.getAttribute( "inputError" ) != null ) { %>
							<div class="alert alert-danger">
								<strong>Error!</strong> <%=request.getAttribute( "inputError" )%>
							</div>
						<%}
						if ( request.getAttribute( "updateError" ) != null ) { %>
							<div class="alert alert-danger">
								<strong>Error!</strong> <%=request.getAttribute( "inputError" )%>
							</div>
						<%}
						if (request.getAttribute("correctUpdate") != null){%>
							<div class="alert ">
								Your email and password have been updated correctly!
							</div>
						<%} %>
					
						<div class="form-group">
							<input type="text" name="email" placeholder="Email..." class="form-control" required>
						</div>
						<div class="form-group">
							<input type="password" name="password" placeholder="Password..." class="form-control" required>
						</div>
						<div class="form-group">
							<input type="password" name="password2" placeholder="Verify password..." class="form-control" required>
						</div>
						
						<div class="form-group row buttons">
							 <div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
							 	<button type="button" class="btn-sm btn cancel">Cancel</button>
							</div>
							
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4"></div>
							
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
							 	<button type="submit" class="btn btn-primary submit">Submit</button>
							</div>
							
						</div>
						
					</form>
				</div>
			
			
				
				
			
			
			</div>
			
		</div>
	
	<jsp:include page="Footer.jsp" /> 
	
	
	</body>
</html>