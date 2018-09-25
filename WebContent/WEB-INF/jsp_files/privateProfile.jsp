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

		<title>User's profile</title>
		
		
	</head>
	<body>
	
		<c:import url="Header.jsp" /> 
		
		<div class="main">
			<div class="container">	
			
			<c:if test="${requestScope.msg != null}">
				<div class="alert alert-success">
					${requestScope.msg}
				</div>
			</c:if>
			
			<c:choose>
	    		<c:when test="${requestScope.pending == 'yes'}">
	    			<c:choose>
			    		<c:when test="${requestScope.sentRequest == '0'}">
			    			<div class="chat">
								<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
									<input type="hidden" name="id" value="${user.id}">
									<input type="hidden" name="pending" value="${user.isPending}">
								    <input type="submit" name="rejectButton" value="Cancel request" class="btn btn-primary btn-lg reject-button"/>
								</form>
							</div>
			    		</c:when>
			    		<c:otherwise>
			    			<div class="chat">
								<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
									<input type="hidden" name="id" value="${param.id}">
									<input type="hidden" name="pending" value="${pending}">
								    <input type="submit" name="rejectButton" value="Reject request" class="btn btn-primary btn-lg reject-button"/>
								    <input type="submit" name="acceptButton" value="Accept request"  class="btn btn-primary btn-lg  accept-button"/>
								</form>
							</div>
			    		</c:otherwise>
			    	</c:choose>

	    		</c:when>
	    		<c:otherwise>
	    			<div class="chat">
						<form action="${pageContext.request.contextPath}/Network" method="POST">
		    				<input type="hidden" name="userId" value="${user_id}" />
						    <input class="btn btn-primary btn-lg chat-button" type="submit" name="connect" value="Connect" />
						</form>
					</div>
	    		</c:otherwise>
	    	</c:choose>
				
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
						    	<td><label>Email:</label>
						    		<c:choose>
							    		<c:when test="${user.privateEmail eq 1}"><em>You can not see this information</em></c:when>
							    		<c:otherwise><c:out value="${user.email}"/></c:otherwise>
							    	</c:choose>
							    </td>	
						    </tr>
						    <tr>
						    	<td><label>Telephone:</label>
						    		<c:choose>
							    		<c:when test="${user.privateTelephone eq 1}"><em>You can not see this information</em></c:when>
							    		<c:otherwise>
							    			<c:choose>
									    		<c:when test="${empty user.tel}"><em>Not set</em></c:when>
									    		<c:otherwise><c:out value="${user.tel}"/></c:otherwise>
									    	</c:choose>
							    		</c:otherwise>
							    	</c:choose>
						    	
						    	 </td>
						    </tr>
						    <tr>
						    	<td><label>Date of birth:</label>
						    		<c:choose>
							    		<c:when test="${user.privateDateOfBirth eq 1}"><em>You can not see this information</em></c:when>
							    		<c:otherwise>
							    			<c:choose>
									    		<c:when test="${empty user.dateOfBirth}"><em>Not set</em></c:when>
									    		<c:otherwise><c:out value="${user.dateOfBirth}"/></c:otherwise>
									    	</c:choose>
							    		</c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td><label>Gender:</label>
						    	
						    		<c:choose>
							    		<c:when test="${user.privateGender eq 1}"><em>You can not see this information</em></c:when>
							    		<c:otherwise>
							    			 <c:choose>
									    		<c:when test="${user.gender == 0}"><em>Not set</em></c:when>
									    		<c:when test="${user.gender == 1}">Male</c:when>
									    		<c:when test="${user.gender == 2}">Female</c:when>
									    		<c:otherwise><em>Not set</em></c:otherwise>
									    	</c:choose>
							    		</c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td>
						    		<div class="row">
							    		<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 ">
							    			<label>Country of residence:</label>
							    			<c:choose>
									    		<c:when test="${user.privateCountry eq 1}"><em>You can not see this information</em></c:when>
									    		<c:otherwise>
									    			<c:choose>
											    		<c:when test="${empty  user.country}"><em>Not set</em></c:when>
											    		<c:otherwise><c:out value="${user.country}"/></c:otherwise>
											    	</c:choose>
									    		</c:otherwise>
									    	</c:choose>
										</div>
	
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6">
							    			<label>City of residence:</label>
							    			
							    			<c:choose>
									    		<c:when test="${user.privateCity eq 1}"><em>You can not see this information</em></c:when>
									    		<c:otherwise>
									    			<c:choose>
											    		<c:when test="${empty  user.city}"><em>Not set</em></c:when>
											    		<c:otherwise><c:out value="${user.city}"/></c:otherwise>
											    	</c:choose>
									    		</c:otherwise>
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
					    		<c:when test="${user.privateWorkPos eq 1}"><em>You can not see this information</em></c:when>
					    		<c:otherwise>
					    			<c:choose>
							    		<c:when test="${empty  user.workPos}"><em>Not set</em></c:when>
							    		<c:otherwise><c:out value="${user.workPos}"/></c:otherwise>
							    	</c:choose>
					    		</c:otherwise>
					    	</c:choose>
							</div>  
						</div>
						<div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Employment institution:</label>
							  
							  <c:choose>
					    		<c:when test="${user.privateInstitution eq 1}"><em>You can not see this information</em></c:when>
					    		<c:otherwise>
					    			<c:choose>
							    		<c:when test="${empty  user.institution}"><em>Not set</em></c:when>
							    		<c:otherwise><c:out value="${user.institution}"/></c:otherwise>
							    	</c:choose>
					    		</c:otherwise>
					    	</c:choose>
							</div>  
						</div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Professional experience:</label>
							  
							  <c:choose>
					    		<c:when test="${user.privateProfExp eq 1}"><em>You can not see this information</em></c:when>
					    		<c:otherwise>
					    			<c:choose>
							    		<c:when test="${empty  user.profExp}"><em>Not set</em></c:when>
							    		<c:otherwise><c:out value="${user.profExp}"/></c:otherwise>
							    	</c:choose>
					    		</c:otherwise>
					    	</c:choose>
							  
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Education:</label>
							  
							   <c:choose>
					    			<c:when test="${user.privateEducation eq 1}"><em>You can not see this information</em></c:when>
						    		<c:otherwise>
						    			<c:choose>
								    		<c:when test="${empty  user.education}"><em>Not set</em></c:when>
								    		<c:otherwise><c:out value="${user.education}"/></c:otherwise>
								    	</c:choose>
						    		</c:otherwise>
						    	</c:choose>
							</div>
						 </div>
						  <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Skills:</label>
							   <c:choose>
					    			<c:when test="${user.privateSkills eq 1}"><em>You can not see this information</em></c:when>
						    		<c:otherwise>
						    			<c:choose>
								    		<c:when test="${empty  user.skills}"><em>Not set</em></c:when>
								    		<c:otherwise><c:out value="${user.skills}"/></c:otherwise>
								    	</c:choose>
						    		</c:otherwise>
						    	</c:choose>
							</div>
						 </div>
						
					</div>
					
				
			</div>
		</div>		
		
		<c:import url="Footer.jsp" /> 
	
	</body>
</html>