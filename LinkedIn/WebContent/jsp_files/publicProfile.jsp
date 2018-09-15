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
		<% if ( request.getAttribute( "redirect" ) == null || request.getAttribute( "redirect" ).equals("null")) { %>
			<jsp:forward page="/PublicProfile">
				<jsp:param name="id" value="${user_id}" ></jsp:param>
			</jsp:forward>
		<% } %>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">					
				<div class="chat">
					<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
						<input type="hidden" name="id" value="${user.id}">
						<input type="hidden" name="pending" value="${user.isPending}">
					    <input type="submit" name="rejectButton" value="Διαγραφή αιτήματος" class="btn btn-primary deleteFriend btn-lg reject-button"/>
					</form>
					<button onclick="location.href='${pageContext.request.contextPath}/jsp_files/Messaging.jsp?id=${user_id}'" type="button" class="btn btn-primary btn-lg chat-button">Συζήτηση</button>
				</div>
				
				<%if (request.getAttribute("msg") != null){%>
					<div class="alert alert-success">
						<%=request.getAttribute("msg")%>
					</div>
				<%} %>
				
				<div class="networkDiv">
					<a href="${pageContext.request.contextPath}/jsp_files/publicNetwork.jsp?id=${user_id}">Δίκτυο<i class="material-icons">people</i></a>
				</div>
	
							
					<table class="table">
						<tbody>
					    	<tr>
						    	<td rowspan="6" class="imageTd" >
							        <div id="uploadedImageDiv">
							         	<img id="uploadedImage" class="profileImage" src="${user.photoURL}"/>
							        </div>
						    	</td>
						    	<td ><label>Ονοματεπώνυμο:</label> <c:out value="${user.name}"/> <c:out value="${user.surname}"/></td>
						    </tr>
						    <tr>
						    	<td><label>Email:</label> <c:out value="${user.email}"/></td>
						    </tr>
						    <tr>
						    	<td><label>Τηλέφωνο:</label>
							    	<c:choose>
							    		<c:when test="${empty user.tel}"><em>Δεν έχει οριστεί.</em></c:when>
							    		<c:otherwise><c:out value="${user.tel}"/></c:otherwise>
							    	</c:choose>
						    	 </td>
						    </tr>
						    <tr>
						    	<td><label>Ημερομηνία γέννησης:</label>
									<c:choose>
							    		<c:when test="${empty  user.dateOfBirth}"><em>Δεν έχει οριστεί.</em></c:when>
							    		<c:otherwise><c:out value="${user.dateOfBirth}"/></c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td><label>Φύλο:</label>
						    		 <c:choose>
							    		<c:when test="${user.gender == 0}"><em>Δεν έχει οριστεί.</em></c:when>
							    		<c:when test="${user.gender == 1}">Άνδρας</c:when>
							    		<c:when test="${user.gender == 2}">Γυναίκα</c:when>
							    		<c:otherwise><em>Δεν έχει οριστεί.</em></c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td>
						    		<div class="row">
							    		<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 ">
							    			<label>Χώρα κατοικίας:</label>
											<c:choose>
									    		<c:when test="${empty  user.country}"><em>Δεν έχει οριστεί.</em></c:when>
									    		<c:otherwise><c:out value="${user.country}"/></c:otherwise>
									    	</c:choose>
										</div>
												
										
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6">
							    			<label>Πόλη κατοικίας:</label>
							    			<c:choose>
									    		<c:when test="${empty  user.city}"><em>Δεν έχει οριστεί.</em></c:when>
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
							  <label>Επαγγελματική θέση:</label>
							  <c:choose>
					    		<c:when test="${empty  user.workPos}"><em>Δεν έχει οριστεί.</em></c:when>
					    		<c:otherwise><p><c:out value="${user.workPos}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						</div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Φορέας απασχόλησης:</label>
							  <c:choose>
					    		<c:when test="${empty  user.institution}"><em>Δεν έχει οριστεί.</em></c:when>
					    		<c:otherwise><p><c:out value="${user.institution}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Επαγγελματική εμπειρία:</label>
							  <c:choose>
					    		<c:when test="${empty  user.profExp}"><em>Δεν έχει οριστεί.</em></c:when>
					    		<c:otherwise><p><c:out value="${user.profExp}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Εκπαίδευση:</label>
							  <c:choose>
					    		<c:when test="${empty  user.education}"><em>Δεν έχει οριστεί.</em></c:when>
					    		<c:otherwise><p><c:out value="${user.education}"/></p></c:otherwise>
					    	 </c:choose>
							</div>
						 </div>
						  <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Δεξιότητες:</label>
							  <c:choose>
					    		<c:when test="${empty  user.skills}"><em>Δεν έχει οριστεί.</em></c:when>
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