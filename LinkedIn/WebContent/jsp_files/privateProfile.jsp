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
		
		<% if ( request.getAttribute( "redirect" ) == null || request.getAttribute( "redirect" ).equals("null")) { %>
			<c:set var="user_id" value="${param.id}" />
			<c:set var="pending" value="${param.pending}" />
			<c:set var="sentRequest" value="${param.sentRequest}" />
		
			<jsp:forward page="/PrivateProfile">
				<jsp:param name="id" value="${user_id}" ></jsp:param>
				<jsp:param name="pending" value="${pending}" ></jsp:param>
				<jsp:param name="sentRequest" value="${sentRequest}" ></jsp:param>
			</jsp:forward>
		<% } %>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">	
			
			<%if (request.getAttribute("msg") != null){%>
				<div class="alert alert-success">
					<%=request.getAttribute("msg")%>
				</div>
			<%} %>

			<% if ( request.getAttribute( "pending" ).equals("yes")) { %>
				
				<% if ( request.getAttribute( "sentRequest" ).equals("0")) { %>
					<div class="chat">
						<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
							<input type="hidden" name="id" value="${user.id}">
							<input type="hidden" name="pending" value="${user.isPending}">
						    <input type="submit" name="rejectButton" value="Ακύρωση αιτήματος" class="btn btn-primary btn-lg reject-button"/>
						</form>
					</div>
				<% }
				else { %>
					<div class="chat">
						<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
							<input type="hidden" name="id" value="${param.id}">
							<input type="hidden" name="pending" value="${pending}">
						    <input type="submit" name="rejectButton" value="Απόρριψη αιτήματος" class="btn btn-primary btn-lg reject-button"/>
						    <input type="submit" name="acceptButton" value="Αποδοχή αιτήματος"  class="btn btn-primary btn-lg  accept-button"/>
						</form>
					</div>
				<%} %> 
	
			<% } 
			else{ %>
				<div class="chat">
					<form action="${pageContext.request.contextPath}/Network" method="POST">
	    				<input type="hidden" name="userId" value="${user_id}" />
					    <input class="btn btn-primary btn-lg chat-button" type="submit" name="connect" value="Σύνδεση" />
					</form>
				</div>
			<% } %> 
				
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
						    	<td><label>Email:</label>
						    		<c:choose>
							    		<c:when test="${user.privateEmail eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
							    		<c:otherwise><c:out value="${user.email}"/></c:otherwise>
							    	</c:choose>
							    </td>	
						    </tr>
						    <tr>
						    	<td><label>Τηλέφωνο:</label>
						    		<c:choose>
							    		<c:when test="${user.privateTelephone eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
							    		<c:otherwise>
							    			<c:choose>
									    		<c:when test="${empty user.tel}"><em>Δεν έχει οριστεί.</em></c:when>
									    		<c:otherwise><c:out value="${user.tel}"/></c:otherwise>
									    	</c:choose>
							    		</c:otherwise>
							    	</c:choose>
						    	
						    	 </td>
						    </tr>
						    <tr>
						    	<td><label>Ημερομηνία γέννησης:</label>
						    		<c:choose>
							    		<c:when test="${user.privateDateOfBirth eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
							    		<c:otherwise>
							    			<c:choose>
									    		<c:when test="${empty user.dateOfBirth}"><em>Δεν έχει οριστεί.</em></c:when>
									    		<c:otherwise><c:out value="${user.dateOfBirth}"/></c:otherwise>
									    	</c:choose>
							    		</c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td><label>Φύλο:</label>
						    	
						    		<c:choose>
							    		<c:when test="${user.privateGender eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
							    		<c:otherwise>
							    			 <c:choose>
									    		<c:when test="${user.gender == 0}"><em>Δεν έχει οριστεί.</em></c:when>
									    		<c:when test="${user.gender == 1}">Άνδρας</c:when>
									    		<c:when test="${user.gender == 2}">Γυναίκα</c:when>
									    		<c:otherwise><em>Δεν έχει οριστεί.</em></c:otherwise>
									    	</c:choose>
							    		</c:otherwise>
							    	</c:choose>
								</td>
						    </tr>
						    <tr>
						    	<td>
						    		<div class="row">
							    		<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 ">
							    			<label>Χώρα κατοικίας:</label>
							    			<c:choose>
									    		<c:when test="${user.privateCountry eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
									    		<c:otherwise>
									    			<c:choose>
											    		<c:when test="${empty  user.country}"><em>Δεν έχει οριστεί.</em></c:when>
											    		<c:otherwise><c:out value="${user.country}"/></c:otherwise>
											    	</c:choose>
									    		</c:otherwise>
									    	</c:choose>
										</div>
	
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6">
							    			<label>Πόλη κατοικίας:</label>
							    			
							    			<c:choose>
									    		<c:when test="${user.privateCity eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
									    		<c:otherwise>
									    			<c:choose>
											    		<c:when test="${empty  user.city}"><em>Δεν έχει οριστεί.</em></c:when>
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
							  <label>Επαγγελματική θέση:</label>
							  
							  <c:choose>
					    		<c:when test="${user.privateWorkPos eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
					    		<c:otherwise>
					    			<c:choose>
							    		<c:when test="${empty  user.workPos}"><em>Δεν έχει οριστεί.</em></c:when>
							    		<c:otherwise><c:out value="${user.workPos}"/></c:otherwise>
							    	</c:choose>
					    		</c:otherwise>
					    	</c:choose>
							</div>  
						</div>
						<div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Φορέας Απασχόλησης:</label>
							  
							  <c:choose>
					    		<c:when test="${user.privateInstitution eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
					    		<c:otherwise>
					    			<c:choose>
							    		<c:when test="${empty  user.institution}"><em>Δεν έχει οριστεί.</em></c:when>
							    		<c:otherwise><c:out value="${user.institution}"/></c:otherwise>
							    	</c:choose>
					    		</c:otherwise>
					    	</c:choose>
							</div>  
						</div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Επαγγελματική εμπειρία:</label>
							  
							  <c:choose>
					    		<c:when test="${user.privateProfExp eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
					    		<c:otherwise>
					    			<c:choose>
							    		<c:when test="${empty  user.profExp}"><em>Δεν έχει οριστεί.</em></c:when>
							    		<c:otherwise><c:out value="${user.profExp}"/></c:otherwise>
							    	</c:choose>
					    		</c:otherwise>
					    	</c:choose>
							  
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Εκπαίδευση:</label>
							  
							   <c:choose>
					    			<c:when test="${user.privateEducation eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
						    		<c:otherwise>
						    			<c:choose>
								    		<c:when test="${empty  user.education}"><em>Δεν έχει οριστεί.</em></c:when>
								    		<c:otherwise><c:out value="${user.education}"/></c:otherwise>
								    	</c:choose>
						    		</c:otherwise>
						    	</c:choose>
							</div>
						 </div>
						  <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12">
							  <label>Δεξιότητες:</label>
							   <c:choose>
					    			<c:when test="${user.privateSkills eq 1}"><em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></c:when>
						    		<c:otherwise>
						    			<c:choose>
								    		<c:when test="${empty  user.skills}"><em>Δεν έχει οριστεί.</em></c:when>
								    		<c:otherwise><c:out value="${user.skills}"/></c:otherwise>
								    	</c:choose>
						    		</c:otherwise>
						    	</c:choose>
							</div>
						 </div>
						
					</div>
					
				
			</div>
		</div>		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>