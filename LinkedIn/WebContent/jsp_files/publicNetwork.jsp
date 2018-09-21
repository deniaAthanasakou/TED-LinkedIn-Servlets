<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<!--<link rel="stylesheet" href="../css_files/main_css.css" type="text/css">
		<link rel="stylesheet" href="../css_files/user_network.css" type="text/css">-->
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/user_network.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/networkNavBar.css" type="text/css">
		
		
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>User's Network</title>
	</head>
	<body>
	
	
		<c:set var="user_id" value="${param.id}" />
		<c:if test="${requestScope.redirect == null}">
			<jsp:forward page="/PublicNetwork">
				<jsp:param name="id" value="${user_id}" ></jsp:param>
			</jsp:forward>
		</c:if>
		
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">
				
				<div class="myContainerPublicNetwork">
					<c:if test="${requestScope.msg != null}">
						<div class="alert alert-success">
							${requestScope.msg}
						</div>
					</c:if>

					<h2 style="font-family:sansserif;font-weight: bold;">Connections</h2>
	  
					    <c:forEach items="${users}" var="user">
							<div class="row">
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >

									<c:choose>
											<c:when test="${user.id!=sessionScope.id}">
												<c:if test="${user.isConnected eq 0}">
										    		<c:choose>
														<c:when test="${user.isPending==1}">
															<c:set var="pending" value="yes" />
															<a href="${pageContext.request.contextPath}/jsp_files/privateProfile.jsp?id=${user.id}&pending=${pending}&sentRequest=${user.sentConnectionRequest}" style="text-decoration:none;">
														</c:when>
														<c:otherwise>
															<c:set var="pending" value="no" />
															<a href="${pageContext.request.contextPath}/jsp_files/privateProfile.jsp?id=${user.id}&pending=${pending}&sentRequest=${user.sentConnectionRequest}" style="text-decoration:none;">
														</c:otherwise>
													</c:choose>
										    	</c:if>
										    	<c:if test="${user.isConnected eq 1}">
										    		<a href="${pageContext.request.contextPath}/jsp_files/publicProfile.jsp?id=${user.id}" style="text-decoration:none;">
										    	</c:if>
											</c:when>
											<c:otherwise>
												<a href="${pageContext.request.contextPath}/jsp_files/profile.jsp" style="text-decoration:none;">
											</c:otherwise>
										</c:choose>	
									
										<table class="myTable">
									    	<tr>
									    		<td rowspan="3"><img  class="img-circle profileImage" src="<c:out value="${user.photoURL}" />"></td>
									    		<td class="nameSurname"><c:out value="${user.name}" /> <c:out value="${user.surname}" /></td>
									    		
									    		<c:choose>
													<c:when test="${user.id!=sessionScope.id}">
														<c:if test="${user.isConnected eq 0}">
															<c:choose>
																<c:when test="${user.isPending==1}">
																
																	<c:choose>
																		<c:when test="${user.sentConnectionRequest==1}"> <!-- the other user sent the request -->
																    		<td rowspan="3">
																    			<div class="buttonClass">
																					<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
																						<input type="hidden" name="id" value="${user.id}">
																						<input type="hidden" name="pending" value="${user.isPending}">
																					    <input type="submit" name="rejectButton" value="Reject request" class="btn btn-primary btn-sm reject-button"/>
																					    <input type="submit" name="acceptButton" value="Accept request"  class="btn btn-primary btn-sm accept-button"/>
																					</form>
																				</div>
																    		</td>
																		</c:when>
																		<c:otherwise>
																    		<td rowspan="3">
																    			<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
																					<input type="hidden" name="id" value="${user.id}">
																					<input type="hidden" name="pending" value="${user.isPending}">
																				    <input type="submit" name="rejectButton" value="Cancel request" class="btn btn-primary reject-button"/>
																				</form>
																    		</td>
																		</c:otherwise>
																	</c:choose>
	
																</c:when>
																<c:otherwise>
																	<td rowspan="2">
												    					<form action="${pageContext.request.contextPath}/Network" method="POST">
														    				<input type="hidden" name="userId" value="${user.id}" />
																		    <input class="btn btn-primary" type="submit" name="connect" value="Connect" />
																		</form>
																	</td>
																</c:otherwise>
															</c:choose>
														

												    	</c:if>
												    	<c:if test="${user.isConnected eq 1}">
												    		<td rowspan="3">
												    			<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
																	<input type="hidden" name="id" value="${user.id}">
																	<input type="hidden" name="pending" value="${user.isPending}">
																    <input type="submit" name="rejectButton" value="Delete connection" class="btn btn-primary reject-button"/>
																</form>
												    		</td>
												    	</c:if>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>	
									    		
									    		
										    </tr>
										     <c:choose>
													<c:when test="${user.id!=sessionScope.id}">
														<c:if test="${user.isConnected==0}">
															<tr>
																<c:choose>
														    		<c:when test="${user.privateWorkPos eq 1}"><td class="deco-none">Work position: <em>You can not see this information</em></td></c:when>
														    		<c:otherwise>
														    			<td class="deco-none">
																    		<c:choose>
																	    		<c:when test="${empty user.workPos}">Work position: <em>Not set</em></c:when>
																	    		<c:otherwise>Work position: <c:out value="${user.workPos}" /></c:otherwise>
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
												    	</c:if>
												    	<c:if test="${user.isConnected==1}">
												    		 <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.workPos}">Work Position: <em>Not set</em></c:when>
															    		<c:otherwise>Work Position: <c:out value="${user.workPos}" /></c:otherwise>
															    	</c:choose>
																</td>
														    </tr>
														    <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.institution}">Employment institution: <em>Not set</em></c:when>
															    		<c:otherwise>Employment institution: <c:out value="${user.institution}" /></c:otherwise>
															    	</c:choose>
																</td>
														    	
														    </tr>
													    </c:if>
													</c:when>
													<c:otherwise>
														    <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.workPos}">Work Position: <em>Not set</em></c:when>
															    		<c:otherwise>Work Position: <c:out value="${user.workPos}" /></c:otherwise>
															    	</c:choose>
																</td>
														    </tr>
														    <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.institution}">Employment institution: <em>Not set</em></c:when>
															    		<c:otherwise>Employment institution: <c:out value="${user.institution}" /></c:otherwise>
															    	</c:choose>
																</td>
														    	
														    </tr>
													</c:otherwise>
												</c:choose>	
									
									    </table> 
									</a>
								
								</div>
							</div>
							
						  </c:forEach>
	
				
				</div> <!-- myContainer -->
				
		    </div> <!-- container -->
		</div>	<!-- main -->
		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>