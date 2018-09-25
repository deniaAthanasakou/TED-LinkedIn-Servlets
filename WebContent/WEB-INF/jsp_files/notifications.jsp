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
		<c:import url="Header.jsp" /> 
		
		<div class="main">
			<div class="container">	
				<h3>Notifications</h3>
				<br>
				<div class="friendRequests">			        
					<c:if test="${requestScope.msg != null}">
						<div class="alert alert-success">
							${requestScope.msg}
						</div>
					</c:if>

					<c:choose>
						<c:when test="${requestScope.requests != null && requestScope.requests == 'noRequests'}">
							<h4>&nbsp; No new connection requests</h4><br>
						</c:when>
						<c:otherwise>
							<h4>&nbsp; Connection Requests</h4><br>
						</c:otherwise>
					</c:choose>
			        
			        
			        <c:forEach items="${usersWithRequests}" var="user">
							<div class="row">
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
									<c:set var="pending" value="yes" />
									<c:set var="sentRequest" value="yes" />
									<a href="${pageContext.request.contextPath}/PrivateProfile?id=${user.id}&pending=${pending}&sentRequest=${sentRequest}" style="text-decoration:none;">
								
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
									    		<c:when test="${user.privateWorkPos eq 1}"><td class="deco-none">Work Position: <em>You can not see this information</em></td></c:when>
									    		<c:otherwise>
									    			<td class="deco-none">
											    		<c:choose>
												    		<c:when test="${empty user.workPos}">Work Position: <em>Not set</em></c:when>
												    		<c:otherwise>Work Position: <c:out value="${user.workPos}" /></c:otherwise>
												    	</c:choose>
													</td>
									    		</c:otherwise>
									    	</c:choose>
								    	</tr>
								    	
								    	<tr>
											<c:choose>
									    		<c:when test="${user.privateInstitution eq 1}"><td class="deco-none">Employment institution: <em>You can not see this information</em></td></c:when>
									    		<c:otherwise>
									    			<td class="deco-none">
											    		<c:choose>
												    		<c:when test="${empty user.institution}">Employment institution: <em>Not set</em></c:when>
												    		<c:otherwise>Employment institution: <c:out value="${user.institution}" /></c:otherwise>
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
					<c:choose>
						<c:when test="${requestScope.postNotifications != null && requestScope.postNotifications == 'noPostNotifications'}">
							<h4>&nbsp; No new Likes &amp; Comments</h4><br>
						</c:when>
						<c:otherwise>
							<h4>&nbsp; Likes &amp; Comments</h4><br>
						</c:otherwise>
					</c:choose>
			        
					<c:forEach items="${usersWithPosts}" var="userPost">
						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
								<c:set var="pending" value="yes" />
								<c:set var="sentRequest" value="yes" />
								<a href="${pageContext.request.contextPath}/PostView?post_id=${userPost.postId}" style="text-decoration:none;">
							
									<table class="requestTable">
							    		<tr>
								    		<td> <a href="${pageContext.request.contextPath}/PublicProfile?id=${userPost.id}"><img  class="img-circle profileImage" src="<c:out value="${userPost.photoURL}" />"></a></td>
									    	<c:choose>
									    		<c:when test="${userPost.isComment == 1}"><td class="notificationInfo">User <a href="${pageContext.request.contextPath}/PublicProfile?id=${userPost.id}"><c:out value="${userPost.name}" /> <c:out value="${userPost.surname}" /></a> commented on your post</td>
									    		</c:when>
									    		<c:otherwise><td class="notificationInfo">User <a href="${pageContext.request.contextPath}/PublicProfile?id=${userPost.id}"><c:out value="${userPost.name}" /> <c:out value="${userPost.surname}" /></a> liked your post</td>
									    		</c:otherwise>
									    	</c:choose>
									    </tr>	

								    </table> 
								</a>
						
							</div>
						</div>
						
					</c:forEach>
					
			    </div>	
			</div>
		</div>		
		
		<c:import url="Footer.jsp" /> 
	
	</body>
</html>