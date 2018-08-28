<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/profile.css" type="text/css">
			
		<link href="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.js"></script>				

		<script src="${pageContext.request.contextPath}/js_files/handleImage.js"></script>

		<title>User's profile</title>
		
		
	</head>
	<body>
	
		<% if ( request.getAttribute( "redirect" ) == null) { %>
			<jsp:forward page="/Profile?action=getUser" />
		<% } %>
	
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
		
					
					<div class="choosePrivate">
						<label>Δημόσιες (<span class="glyphicon glyphicon-ok"></span>) και ιδιωτικές (<span class="glyphicon glyphicon-remove"></span>) πληροφορίες:</label>
						<br><br>
						<span class="glyphicon glyphicon-ok"></span> Ονοματεπώνυμο</p>
						<c:choose>
				    		<c:when test="${user.privateEmail==1}"><p><span class="glyphicon glyphicon-remove"></span> Ηλεκτρονική διέυθυνση/Email</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Ηλεκτρονική διέυθυνση/Email</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateTelephone==1}"><p><span class="glyphicon glyphicon-remove"></span> Τηλέφωνο</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Τηλέφωνο</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateDateOfBirth==1}"><p><span class="glyphicon glyphicon-remove"></span> Ημερομηνία γέννησης</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Ημερομηνία γέννησης</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateGender==1}"><p><span class="glyphicon glyphicon-remove"></span> Φύλο</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Φύλο</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateCountry==1}"><p><span class="glyphicon glyphicon-remove"></span> Χώρα κατοικίας</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Χώρα κατοικίας</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateCity==1}"><p><span class="glyphicon glyphicon-remove"></span> Πόλη/Περιοχή κατοικίας</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Πόλη/Περιοχή κατοικίας</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateProfExp==1}"><p><span class="glyphicon glyphicon-remove"></span> Επαγγελματική Εμπειρία</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Επαγγελματική Εμπειρία</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateEducation==1}"><p><span class="glyphicon glyphicon-remove"></span> Εκπαίδευση</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Εκπαίδευση</p></c:otherwise>
				    	</c:choose>
				    	
				    	<c:choose>
				    		<c:when test="${user.privateSkills==1}"><p><span class="glyphicon glyphicon-remove"></span> Δεξιότητες</p></c:when>
				    		<c:otherwise><p><span class="glyphicon glyphicon-ok"></span> Δεξιότητες</p></c:otherwise>
				    	</c:choose>

					</div>
					
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/Profile" accept-charset="UTF-8">
						<div class="editDiv row">
							<div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 ">
						 		<button type="submit" class="btn btn-primary editButton">Επεξεργασία</button>
						 	</div>
						</div>
					</form>
				
			</div>
		</div>		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>