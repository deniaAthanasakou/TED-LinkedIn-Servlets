<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/notifications.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/user_network.css" type="text/css">			

		<title>Notifications</title>
		
		
	</head>
	<body>
	
		<% if ( request.getAttribute( "redirect" ) == null) { %>
			<jsp:forward page="/Notifications" />
		<% } %>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">	
				<h3>Ειδοποιήσεις</h3>
				<br>
				<div class="friendRequests">			        
			        <%if (request.getAttribute("msg") != null){%>
						<div class="alert alert-success">
							<%=request.getAttribute("msg")%>
						</div>
					<%} %>
					
					<% if ( request.getAttribute( "requests" )!=null && request.getAttribute( "requests" ).equals("noRequests") ) { %>
						<h4>&nbsp; Δεν έχετε νέα αιτήματα σύνδεσης</h4><br>
					<%}
					else{%>
						<h4>&nbsp; Αιτήματα Σύνδεσης</h4><br>
					<%} %>
			        
			        
			        <c:forEach items="${usersWithRequests}" var="user">
							<div class="row">
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
									<c:set var="pending" value="yes" />
									<c:set var="sentRequest" value="yes" />
									<a href="${pageContext.request.contextPath}/jsp_files/privateProfile.jsp?id=${user.id}&pending=${pending}&sentRequest=${sentRequest}" style="text-decoration:none;">
								
									<table class="requestTable">
								    	<tr>
								    		<td rowspan="3"><img  class="img-circle profileImage" src="<c:out value="${user.photoURL}" />"></td>
								    		<td class="nameSurname"><c:out value="${user.name}" /> <c:out value="${user.surname}" /></td>

								    		<td rowspan="3">
								    			<div class="buttonClass">
													<form action="${pageContext.request.contextPath}/Notifications" method="POST">
														<input type="hidden" name="id" value="${user.id}">
														<input type="hidden" name="pending" value="${user.isPending}">
													    <input type="submit" name="rejectButton" value="Απόρριψη αιτήματος" class="btn btn-primary btn-sm reject-button"/>
													    <input type="submit" name="acceptButton" value="Αποδοχή αιτήματος"  class="btn btn-primary btn-sm accept-button"/>
													</form>
												</div>
								    		</td>
									    </tr>	
										<tr>
											<c:choose>
									    		<c:when test="${user.privateWorkPos eq 1}"><td class="deco-none">Επαγγελματική Θέση: <em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></td></c:when>
									    		<c:otherwise>
									    			<td class="deco-none">
											    		<c:choose>
												    		<c:when test="${empty user.workPos}">Επαγγελματική Θέση: <em>Δεν έχει οριστεί.</em></c:when>
												    		<c:otherwise>Επαγγελματική Θέση: <c:out value="${user.workPos}" /></c:otherwise>
												    	</c:choose>
													</td>
									    		</c:otherwise>
									    	</c:choose>
								    	</tr>
								    	
								    	<tr>
											<c:choose>
									    		<c:when test="${user.privateInstitution eq 1}"><td class="deco-none">Φορέας Απασχόλησης: <em>Δεν μπορείτε να δείτε αυτή την πληροφορία.</em></td></c:when>
									    		<c:otherwise>
									    			<td class="deco-none">
											    		<c:choose>
												    		<c:when test="${empty user.institution}">Φορέας Απασχόλησης: <em>Δεν έχει οριστεί.</em></c:when>
												    		<c:otherwise>Φορέας Απασχόλησης: <c:out value="${user.institution}" /></c:otherwise>
												    	</c:choose>
													</td>
									    		</c:otherwise>
									    	</c:choose>
								    	</tr>	
								
								    </table> 
								</a>
						
							</div>
						</div>
						
					  </c:forEach>
		        
			    </div>
			    <hr>
			    <div class="likesComments">
					<% if ( request.getAttribute( "postNotifications" )!=null && request.getAttribute( "postNotifications" ).equals("noPostNotifications") ) { %>
						<h4>&nbsp; Δεν έχετε νέα Likes & Comments</h4><br>
					<%}
					else{%>
						<h4>&nbsp; Likes & Comments</h4><br>
					<%} %>
			        
					<c:forEach items="${usersWithPosts}" var="userPost">
						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
								<c:set var="pending" value="yes" />
								<c:set var="sentRequest" value="yes" />
								<a href="${pageContext.request.contextPath}/jsp_files/postPage.jsp?id=${userPost.postId}" style="text-decoration:none;">
							
									<table class="requestTable">
							    		<tr>
								    		<td> <a href="${pageContext.request.contextPath}/jsp_files/publicProfile.jsp?id=${userPost.id}"><img  class="img-circle profileImage" src="<c:out value="${userPost.photoURL}" />"></a></td>
								    		<td class="nameSurname deco-none"> Ο χρήστης <a href="${pageContext.request.contextPath}/jsp_files/publicProfile.jsp?id=${userPost.id}"><c:out value="${userPost.name}" /> <c:out value="${userPost.surname}" /></a> σημείωσε ενδιαφέρον για μια ανάρτησή σας</td>
								    		<td><button type="button" class="btn btn-primary disabled">Seen</button></td>
									    </tr>	

								    </table> 
								</a>
						
							</div>
						</div>
						
					</c:forEach>
					
			    </div>	
			</div>
		</div>		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>