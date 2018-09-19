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

		<c:if test="${requestScope.redirectJobs == null}">
			<jsp:forward page="/JobHandle?action=getJobs" />
		</c:if>
	
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
					<c:forEach items="${sessionJobs}" var="job">
						<c:if test="${job.id.userId == sessionScope.id}">
							<div class="jobItem">
								<form method="post" id="mine${job.id.jobId}" action="${pageContext.request.contextPath}/JobHandle?action=getJob&id=${job.id.jobId}">
									<div onclick="document.getElementById('mine${job.id.jobId}').submit();">
										<img src="${pageContext.request.contextPath}/images/company-name.png">
										<h3>${job.title}</h3>
										<h4>${job.company}</h4>
										<h5>${job.location}</h5>
										<p>${job.dateInterval}</p>
									</div>
								</form>
							</div>
						</c:if>
					</c:forEach>
				</div>
			
				<!-- from connections -->
				<div class="jobsLabel">
					<h4>Από τις συνδέσεις</h4>
				</div>
				
				<div class="jobsSection">
					<c:forEach items="${connJobs}" var="job">
						<div class="jobItem">
							<form method="post" id="conn${job.id.jobId}" action="${pageContext.request.contextPath}/JobHandle?action=getJob&id=${job.id.jobId}">
								<div onclick="document.getElementById('conn${job.id.jobId}').submit();">
									<img src="${pageContext.request.contextPath}/images/company-name.png">
									<h3>${job.title}</h3>
									<h4>${job.company}</h4>
									<h5>${job.location}</h5>
									<p>${job.dateInterval}</p>
								</div>
							</form>
						</div>
					</c:forEach>
				</div>
				
				<!-- from skills -->
				<div class="jobsLabel">
					<h4>Βασισμένο σε δεξιότητες προφίλ</h4>
				</div>
				
				<div class="jobsSection">
					<c:forEach items="${skillJobs}" var="job">
						<c:if test="${job.id.userId != sessionScope.id }">
							<div class="jobItem">
								<form method="post" id="skills${job.id.jobId}" action="${pageContext.request.contextPath}/JobHandle?action=getJob&id=${job.id.jobId}">
									<div onclick="document.getElementById('skills${job.id.jobId}').submit();">
										<img src="${pageContext.request.contextPath}/images/company-name.png">
										<h3>${job.title}</h3>
										<h4>${job.company}</h4>
										<h5>${job.location}</h5>
										<p>${job.dateInterval}</p>
									</div>
								</form>
							</div>
						</c:if>
					</c:forEach>
				</div>
				
				<!-- from network -->
				<div class="jobsLabel">
					<h4>Βασισμένο σε δεδομένα</h4>
				</div>
				
				<div class="jobsSection">
					<c:forEach items="${jobs}" var="job">
						<div class="jobItem">
							<form method="post" id="data${job.id.jobId}" action="${pageContext.request.contextPath}/JobHandle?action=getJob&id=${job.id.jobId}">
								<div onclick="document.getElementById('data${job.id.jobId}').submit();">
									<img src="${pageContext.request.contextPath}/images/company-name.png">
									<h3>${job.title}</h3>
									<h4>${job.company}</h4>
									<h5>${job.location}</h5>
									<p>${job.dateInterval}</p>
								</div>
							</form>
						</div>
					</c:forEach>
				</div>
			
			</div>
		</div>
		
		<jsp:include page="Footer.jsp"/>
	</body>
</html>