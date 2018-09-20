<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/jobItem.css" type="text/css">
		<script src="${pageContext.request.contextPath}/js_files/showHide.js"></script>
		<title>Job Additional Info</title>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="mainInfoDesc">
				<div class="row">
					<div class="col-md-4">
						<img src="${pageContext.request.contextPath}/images/company-name.png">
					</div>
					<div class="col-md-8">
						<h2>${job.title}</h2>
						<h3>${job.company}</h3>
						<h5 style="color:#777777">${job.location}</h5>
						<p style="color:#ccc">${job.dateInterval}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
					</div>
					<div class="col-md-8">
						<c:choose>
							<c:when test="${job.id.userId != sessionScope.id}">
								<form method="post" action="${pageContext.request.contextPath}/JobApplicationHandle" accept-charset="UTF-8">
									<input type="hidden" name="jobId" value="${job.id.jobId}">
									<c:if test="${applied == 0}">
										<button type="submit" class="btn btn-primary btn-lg" name="apply">Αίτηση</button>
									</c:if>
									<c:if test="${applied == 1}">
										<button type="submit" class="btn btn-primary btn-lg disabled">Applied</button>
									</c:if>
								</form>
							</c:when>
							<c:otherwise>
								<div class="row">
									<div class="col-md-6">
										<form method="post" action="${pageContext.request.contextPath}/JobHandle" accept-charset="UTF-8">
											<button type="submit" class="btn btn-success btn-lg" name="edit" value="${job.id.jobId}">Επεξεργασία</button>
										</form>
									</div>
									<div class="col-md-6">
										<form method="post" action="${pageContext.request.contextPath}/JobApplicationHandle" accept-charset="UTF-8">
											<input type="hidden" name="jobId" value="${job.id.jobId}">
											<button type="submit" class="btn btn-info btn-lg" name="list" value="${job.id.jobId}">Αιτηθέντες</button>
										</form>
									</div>
								</div>
								
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			
			<div class="additionalInfo">
				<div class="row" id="switchContent">
					<div class="col-md-9">
						<h3>Περιγραφή Εργασίας</h3>
						<p>${job.description}</p>
					</div>
					<div class="col-md-3">
						<h4 style="padding-bottom:5px;">Πώς ταιριάζεις</h4>
						<h5 style="text-decoration:underline;">Δεξιότητες</h5>
						<ul style="font-size: 15px;color: gray;">
							<c:forEach items="${job.skillsArray}" var="skill">
								<li>${skill}</li>
							</c:forEach>
						</ul>
						<h5 style="text-decoration:underline;">Επίπεδο εκπαίδευσης</h5>
						<p style="border-bottom: 1px solid #ccc;padding-bottom:10px;font-size: 15px;">${job.educationLevelStr}</p>
						<h4 style="padding-bottom:5px;">Λεπτομέρειες εργασίας</h4>
						<h5 style="text-decoration:underline;">Είδος εμπειρίας</h5>
						<p style="font-size: 15px;">${job.experience}</p>
						<h5 style="text-decoration:underline;">Είδος εταιρείας</h5>
						<p style="font-size: 15px;">${job.companyTypeStr}</p>
						<h5 style="text-decoration:underline;">Τύπος εργασίας</h5>
						<p style="font-size: 15px;">${job.jobType}</p>
						<h5 style="text-decoration:underline;">Λειτουργίες εργασίας</h5>
						<p style="font-size: 15px;">${job.jobFunctionStr}</p>
						<h5 style="text-decoration:underline;">Χρόνια εμπειρίας</h5>
						<p style="font-size: 15px;">${job.experienceFrom} έως ${job.experienceTo}</p>
						<h5 style="text-decoration:underline;">Ημερήσιος μισθός</h5>
						<p style="font-size: 15px;">${job.dailySalary}</p>
					</div>
				</div>
				<div class="row show-more">
					<span><input type="button" id="showHide" onclick="displayDesc();" value="See more"> <i id="showHideGlyph" class="glyphicon glyphicon-chevron-down"></i></span>
				</div>
			</div>
		</div>
		
		<jsp:include page="Footer.jsp" />

	</body>
</html>