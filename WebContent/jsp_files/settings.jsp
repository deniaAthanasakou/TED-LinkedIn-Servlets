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
	
	
	<c:if test="${requestScope.redirect == null}">
		<jsp:forward page="/Settings?action=getCredentials" />
	</c:if>
	
	<jsp:include page="Header.jsp" /> 
	
	<div class="main">
			<div class="container">				
				<div class="settingsBox">
					<h3>Change your email address and/or password</h3>
					<br><br>
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/Settings" accept-charset="UTF-8">
					
						<c:if test="${requestScope.inputError != null}">
							<div class="alert alert-danger">
								<strong>Error!</strong> ${requestScope.inputError}
							</div>
						</c:if>
						
						<c:if test="${requestScope.updateError != null}">
							<div class="alert alert-danger">
								<strong>Error!</strong> ${requestScope.updateError}
							</div>
						</c:if>
						<c:if test="${requestScope.correctUpdate != null}">
							<div class="alert alert-success">
								Your email and password have been updated!
							</div>
						</c:if>

					
						<div class="form-group">
							<label for="emailInput">Email address</label>
							<input type="text" name="email" id="emailInput" placeholder="Email..." class="form-control" value="${user.email}" required>
						</div>
						<div class="form-group">
							<label for="passwordInput">Password</label>
							<input type="password" name="password" id="passwordInput" placeholder="Password..." class="form-control" value="${user.password}" required>
						</div>
						<div class="form-group">
							<label for="passwordVerInput">Password Verification</label>
							<input type="password" name="password2" id="passwordVerInput" placeholder="Verify password..." class="form-control" value="${user.password}" required>
						</div>
						
						<div class="form-group row buttons">
							 <div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
							 	<input type="reset" class="btn cancel" value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/Settings';">
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