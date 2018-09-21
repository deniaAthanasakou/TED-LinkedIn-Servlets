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
						<h2><c:out value="${job.title}"/></h2>
						<h3><c:out value="${job.company}"/></h3>
						<h5 style="color:#777777"><c:out value="${job.location}"/></h5>
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
											<button type="submit" class="btn btn-success btn-lg" name="edit" value="${job.id.jobId}">Edit</button>
										</form>
									</div>
									<div class="col-md-6">
										<form method="post" action="${pageContext.request.contextPath}/JobApplicationHandle" accept-charset="UTF-8">
											<input type="hidden" name="jobId" value="${job.id.jobId}">
											<button type="submit" class="btn btn-info btn-lg" name="list" value="${job.id.jobId}">Appliers</button>
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
						<h3>Job description</h3>
						<p><c:out value="${job.description}"/></p>
					</div>
					<div class="col-md-3">
						<h4 style="padding-bottom:5px;">How you match</h4>
						<h5 style="text-decoration:underline;">Skills</h5>
						<ul style="font-size: 15px;color: gray;">
							<c:forEach items="${job.skillsArray}" var="skill">
								<li><c:out value="${skill}"/></li>
							</c:forEach>
						</ul>
						<h5 style="text-decoration:underline;">Education level</h5>
						<p style="border-bottom: 1px solid #ccc;padding-bottom:10px;font-size: 15px;"><c:out value="${job.educationLevelStr}"/></p>
						<h4 style="padding-bottom:5px;">Job details</h4>
						<h5 style="text-decoration:underline;">Experience level</h5>
						<p style="font-size: 15px;"><c:out value="${job.experience}"/></p>
						<h5 style="text-decoration:underline;">Company type</h5>
						<p style="font-size: 15px;"><c:out value="${job.companyTypeStr}"/></p>
						<h5 style="text-decoration:underline;">Job type</h5>
						<p style="font-size: 15px;"><c:out value="${job.jobType}"/></p>
						<h5 style="text-decoration:underline;">Job functions</h5>
						<p style="font-size: 15px;"><c:out value="${job.jobFunctionStr}"/></p>
						<h5 style="text-decoration:underline;">Years of experience</h5>
						<p style="font-size: 15px;">${job.experienceFrom} to ${job.experienceTo}</p>
						<h5 style="text-decoration:underline;">Daily salary</h5>
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