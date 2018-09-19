<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/profile.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/profileNavBar.css" type="text/css">
			
		<link href="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.js"></script>				

		<script src="${pageContext.request.contextPath}/js_files/handleImage.js"></script>

		<title>Profile</title>
		
		
	</head>
	<body>
	
		<c:if test="${requestScope.redirect == null}">
			<jsp:forward page="/Profile?action=getUser" />
		</c:if>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">	
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
		
					
					<div class="choosePrivate">
						<label>Public (<span class="glyphicon glyphicon-ok"></span>) and private (<span class="glyphicon glyphicon-remove"></span>) information:</label>
						<br><br>
						<p><span class="glyphicon glyphicon-ok"></span> Full name</p>
						<c:choose>
				    		<c:when test="${user.privateEmail==1}"><p><span class="glyphicon glyphicon-remove"></span> Email</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Email</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateTelephone==1}"><p><span class="glyphicon glyphicon-remove"></span> Telephone</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Telephone</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateDateOfBirth==1}"><p><span class="glyphicon glyphicon-remove"></span> Date of birth</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Date of birth</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateGender==1}"><p><span class="glyphicon glyphicon-remove"></span> Gender</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Gender</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateCountry==1}"><p><span class="glyphicon glyphicon-remove"></span> Country of residence</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Country of residence</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateCity==1}"><p><span class="glyphicon glyphicon-remove"></span> City of residence</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> City of residence</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateWorkPos==1}"><p><span class="glyphicon glyphicon-remove"></span> Work position</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Work position</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateInstitution==1}"><p><span class="glyphicon glyphicon-remove"></span> Employment institution</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Employment institution</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateProfExp==1}"><p><span class="glyphicon glyphicon-remove"></span> Professional experience</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Professional experience</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateEducation==1}"><p><span class="glyphicon glyphicon-remove"></span> Education</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span>  Education</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateSkills==1}"><p><span class="glyphicon glyphicon-remove"></span> Skills</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Skills</p></c:otherwise>
				    	</c:choose>

					</div>
					
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/Profile" accept-charset="UTF-8">
						<div class="editDiv row">
							<div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 ">
						 		<button type="submit" class="btn btn-primary editButton">Edit</button>
						 	</div>
						</div>
					</form>
				
			</div>
		</div>		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>