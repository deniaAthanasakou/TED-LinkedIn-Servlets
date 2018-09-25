<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/jobs.css" type="text/css">
		<title>Jobs</title>
	</head>
	<body>
		<c:import url="Header.jsp" /> 
		
		<div class="main">
			<div class="createJob">
				<div class="applied_stats">
					<p style="color:#E1E1E1;">${noApplied}</p>
					<h4 style="color:#939393;">Applied jobs</h4>
				</div>
				<div class="newJob">
					<h4 style="color:#939393;">Looking for talent?</h4>
					<a href="${pageContext.request.contextPath}/jsp_files/createJob.jsp"><button class="btn btn-primary"><i class="glyphicon glyphicon-edit"></i> Post a job</button></a>
				</div>
			</div>
			
			<div class="displayJobs">
				<!-- from my applications -->
				<div class="jobsLabel">
					<h4>My jobs</h4>
				</div>
				
				<div class="jobsSection">
					<c:forEach items="${sessionJobs}" var="job">
						<c:if test="${job.id.userId == sessionScope.id}">
							<div class="jobItem">
								<form method="post" id="mine${job.id.jobId}" action="${pageContext.request.contextPath}/JobHandle?action=getJob&id=${job.id.jobId}">
									<div onclick="document.getElementById('mine${job.id.jobId}').submit();">
										<img src="${pageContext.request.contextPath}/images/company-name.png">
										<h3><c:out value="${job.title}"/></h3>
										<h4><c:out value="${job.company}"/></h4>
										<h5><c:out value="${job.location}"/></h5>
										<p>${job.dateInterval}</p>
									</div>
								</form>
							</div>
						</c:if>
					</c:forEach>
				</div>
			
				<!-- from connections -->
				<div class="jobsLabel">
					<h4>From connections</h4>
				</div>
				
				<div class="jobsSection">
					<c:forEach items="${connJobs}" var="job">
						<div class="jobItem">
							<form method="post" id="conn${job.id.jobId}" action="${pageContext.request.contextPath}/JobHandle?action=getJob&id=${job.id.jobId}">
								<div onclick="document.getElementById('conn${job.id.jobId}').submit();">
									<img src="${pageContext.request.contextPath}/images/company-name.png">
									<h3><c:out value="${job.title}"/></h3>
									<h4><c:out value="${job.company}"/></h4>
									<h5><c:out value="${job.location}"/></h5>
									<p>${job.dateInterval}</p>
								</div>
							</form>
						</div>
					</c:forEach>
				</div>
				
				<!-- from data -->
				<div class="jobsLabel">
					<h4>Based on data</h4>
				</div>
				
				<div class="jobsSection">
					<c:forEach items="${jobs}" var="job">
						<div class="jobItem">
							<form method="post" id="data${job.id.jobId}" action="${pageContext.request.contextPath}/JobHandle?action=getJob&id=${job.id.jobId}">
								<div onclick="document.getElementById('data${job.id.jobId}').submit();">
									<img src="${pageContext.request.contextPath}/images/company-name.png">
									<h3><c:out value="${job.title}"/></h3>
									<h4><c:out value="${job.company}"/></h4>
									<h5><c:out value="${job.location}"/></h5>
									<p>${job.dateInterval}</p>
								</div>
							</form>
						</div>
					</c:forEach>
				</div>
			
			</div>
		</div>
		
		<c:import url="Footer.jsp"/>
	</body>
</html>