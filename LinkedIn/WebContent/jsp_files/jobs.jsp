<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/jobs.css" type="text/css">
		<title>Jobs</title>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="createJob">
				<div class="applied_stats">
					<p style="color:#E1E1E1;">0</p>
					<h4 style="color:#939393;">Αιτηθέντες Αγγελίες</h4>
				</div>
				<div class="newJob">
					<h4 style="color:#939393;">Ψάχνεις για ταλέντο;</h4>
					<a href="${pageContext.request.contextPath}/jsp_files/createJob.jsp"><button class="btn btn-primary"><i class="glyphicon glyphicon-edit"></i> Δημιουργία Αγγελίας</button></a>
				</div>
			</div>
			
			<div class="displayJobs">
				<!-- from my applications -->
				<div class="jobsLabel">
					<h4>Οι αγγελίες μου</h4>
				</div>
				
				<div class="jobsSection">
				
				</div>
			
				<!-- from connections -->
				<div class="jobsLabel">
					<h4>Από τις συνδέσεις</h4>
				</div>
				
				<div class="jobsSection">
					<div class="container-fluid noPadding">
						<div class="row">
							<div class="col-md-4">
								<div class="jobItem" onclick="location.href='${pageContext.request.contextPath}/jsp_files/jobItem.jsp';">
									<img src="${pageContext.request.contextPath}/images/company-name.png">
									<h3>JobTitle</h3>
									<h4>JobIndustry</h4>
									<h5>JobLocation</h5>
									<p>Date posted</p>
								</div>
							</div>
							<div class="col-md-4">
								<div class="jobItem">
									<img src="${pageContext.request.contextPath}/images/company-name.png">
									<h3>JobTitle</h3>
									<h4>JobIndustry</h4>
									<h5>JobLocation</h5>
									<p>Date posted</p>
								</div>
							</div>
							<div class="col-md-4">
								<div class="jobItem">
									<img src="${pageContext.request.contextPath}/images/company-name.png">
									<h3>JobTitle</h3>
									<h4>JobIndustry</h4>
									<h5>JobLocation</h5>
									<p>Date posted</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- from skills -->
				<div class="jobsLabel">
					<h4>Βασισμένο σε δεξιότητες προφίλ</h4>
				</div>
				
				<div class="jobsSection">
				
				</div>
				
				<!-- from network -->
				<div class="jobsLabel">
					<h4>Βασισμένο σε δεδομένα</h4>
				</div>
				
				<div class="jobsSection">
				
				</div>
			</div>
			
		</div>
		
		<jsp:include page="Footer.jsp"/>
	</body>
</html>