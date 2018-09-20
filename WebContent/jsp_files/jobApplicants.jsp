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
			<h2>Job Applications</h2>
		
			<c:forEach items="${applicants}" var="applicant">
				<form method="post" action="${pageContext.request.contextPath}/JobApplicationHandle">
					<div class="applicant">
						<div class="row equal">
							<div class="col-md-3">
								<img src="${applicant.photoURL}" width="80px" height="80px" style="border-radius:50%;">
							</div>
							<div class="col-md-4">
								<h3>${applicant.name} ${applicant.surname}</h3>
							</div>
							<div class="col-md-5">
								<input type="hidden" name="userId" value="${applicant.id}">
								<input type="hidden" name="jobId" value="${jobId}">
								<div class="btn-group btn-group-justified">
									<c:choose>
										<c:when test="${approved == 1}">
											<button type="submit" class="btn btn-success disabled"><i class="glyphicon glyphicon-ok-circle"></i> Accepted</button>
								    	</c:when>
								    	<c:otherwise>
										    <div class="btn-group">
										   			<button type="submit" class="btn btn-success" name="accept"><i class="glyphicon glyphicon-ok-circle"></i> Accept</button>
										    </div>
										    <div class="btn-group">
										    	<button type="submit" class="btn btn-warning" name="decline"><i class="glyphicon glyphicon-remove-circle"></i> Reject</button>
									    	</div>
							    		</c:otherwise>
							    	</c:choose>
							    	<button type="submit" class="btn btn-info" name="getProfile"><i class="glyphicon glyphicon-zoom-in"></i> Profile</button>
						    	</div>
							</div>
						</div>
					</div>
				</form>
			</c:forEach>
		</div>
		
		<jsp:include page="Footer.jsp" /> 

	</body>
</html>