<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/profile.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/networkNavBar.css" type="text/css">
			
		<link href="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.js"></script>				

		<script src="${pageContext.request.contextPath}/js_files/handleImage.js"></script>
		
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

		<title>User's profile</title>
		
		
	</head>
	<body>

		<c:set var="user_id" value="${param.id}" />
		<c:if test="${requestScope.redirect == null || requestScope.redirect == 'null'}">
			<jsp:forward page="/PublicProfile">
				<jsp:param name="id" value="${user_id}" ></jsp:param>
			</jsp:forward>
		</c:if>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">					
				<div class="chat">
					<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
						<input type="hidden" name="id" value="${user.id}">
						<input type="hidden" name="pending" value="${user.isPending}">
					    <input type="submit" name="rejectButton" value="Διαγραφή Σύνδεσης" class="btn btn-primary deleteFriend btn-lg reject-button"/>
					</form>
					<a href="${pageContext.request.contextPath}/ConversationHandler?action=conversation&id=${user_id}" type="button" class="btn btn-primary btn-lg chat-button">Chat</a>
				</div>
				
				<c:if test="${requestScope.msg != null}">
					<div class="alert alert-success">
						${requestScope.msg}
					</div>
				</c:if>
				
				<div class="networkDiv">
					<a href="${pageContext.request.contextPath}/jsp_files/publicNetwork.jsp?id=${user_id}">Network<i class="material-icons">people</i></a>
				</div>
	
							
					<table class="table">
						<tbody>
					    	<tr>
						    	<td rowspan="6" class="imageTd" >
							        <div id="uploadedImageDiv">
							         	<img id="uploadedImage" class="profileImage" src="${user.photoURL}"/>
							        </div>
						    	</td>
						    	<td ><label>Full name:</label> <c:out value="${user.name}"/> <c:out value="${user.surname}"/></td>
						    </tr>
						    <tr>
						    	<td><label>Email:</label> <c:out value="${user.email}"/></td>
						    </tr>
						    <tr>
						    	<td><label>Telephone:</label>
							    	<c:choose>
							    		<c:when test="${empty user.tel}"><em>Not set</em></c:when>
							    		<c:otherwise><c:out value="${user.tel}"/></c:otherwise>
							    	</c:choose>
						    	 </td>
						    </tr>
						    <tr>
						    	<td><label>Date of birth:</label>
									<c:choose>
							    		<c:when test="${empty  user.dateOfBirth}"><em>Not set</em></c:when>
							    		<c:otherwise><c:out value="${user.dateOfBirth}"/></c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td><label>Gender:</label>
						    		 <c:choose>
							    		<c:when test="${user.gender == 0}"><em>Not set</em></c:when>
							    		<c:when test="${user.gender == 1}">Male</c:when>
							    		<c:when test="${user.gender == 2}">Female</c:when>
							    		<c:otherwise><em>Not set</em></c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td>
						    		<div class="row">
							    		<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 ">
							    			<label>Country of residence:</label>
											<c:choose>
									    		<c:when test="${empty  user.country}"><em>Not set</em></c:when>
									    		<c:otherwise><c:out value="${user.country}"/></c:otherwise>
									    	</c:choose>
										</div>
												
										
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6">
							    			<label>City of residence:</label>
							    			<c:choose>
									    		<c:when test="${empty  user.city}"><em>Not set</em></c:when>
									    		<c:otherwise><c:out value="${user.city}"/></c:otherwise>
									    	</c:choose>
										</div>
									</div>
								</td>
						    </tr>
					    </tbody>
					 </table>
					
					 
					 
					 <div class="info">
					 	<div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Work position:</label>
							  <c:choose>
					    		<c:when test="${empty  user.workPos}"><em>Not set</em></c:when>
					    		<c:otherwise><p><c:out value="${user.workPos}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						</div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Employment institution:</label>
							  <c:choose>
					    		<c:when test="${empty  user.institution}"><em>Not set</em></c:when>
					    		<c:otherwise><p><c:out value="${user.institution}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Professional experience:</label>
							  <c:choose>
					    		<c:when test="${empty  user.profExp}"><em>Not set</em></c:when>
					    		<c:otherwise><p><c:out value="${user.profExp}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Education:</label>
							  <c:choose>
					    		<c:when test="${empty  user.education}"><em>Not set</em></c:when>
					    		<c:otherwise><p><c:out value="${user.education}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						 </div>
						  <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Skills:</label>
							  <c:choose>
					    		<c:when test="${empty  user.skills}"><em>Not set</em></c:when>
					    		<c:otherwise><p><c:out value="${user.skills}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						 </div>
						
					</div>
					
				
			</div>
		</div>		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>