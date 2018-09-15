<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/jobApplicants.css" type="text/css">
		<title>Job Applicants</title>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<h2>Αιτήσεις Εργασίας</h2>
		
			<div class="applicant">
				<div class="row equal">
					<div class="col-md-3">
						<img src="${pageContext.request.contextPath}/images/default-user.png" width="80px" height="80px" style="border-radius:50%;">
					</div>
					<div class="col-md-4">
						<h3>Name</h3>
						<h3>Surname</h3>
					</div>
					<div class="col-md-5">
						<div class="btn-group btn-group-justified">
						    <div class="btn-group">
						   		<button type="button" class="btn btn-success"><i class="glyphicon glyphicon-ok-circle"></i> Αποδοχή</button>
						    </div>
						    <div class="btn-group">
						    	<button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-remove-circle"></i> Απόρριψη</button>
					    	</div>
				    	</div>
					</div>
				</div>
			</div>
		
		</div>
		
		<jsp:include page="Footer.jsp" /> 

	</body>
</html>