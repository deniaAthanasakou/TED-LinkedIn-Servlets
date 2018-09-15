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
		<title>Network</title>
	</head>
	<body>
	
	
	<% if ( request.getAttribute( "redirect" ) == null  || request.getAttribute( "redirect" ).equals("null")) { %>
		<jsp:forward page="/Network?action=getConnectedUsers" />
	<% } %>
	
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">
				<div class="searchContainer" >
			        
			        <br>
			         <form role="Form" method="POST" action="${pageContext.request.contextPath}/Network" accept-charset="UTF-8" >
			          
					  <div class="row">
					  
						  <div class="col-xs-3 col-md-3 col-lg-3 col-sm-3"></div> <!-- for alignment purposes -->
						  
						    <div class="col-xs-6 col-md-6 col-lg-6 col-sm-6">
						    
						    
						      <div class="input-group">
						        <input type="text" class="form-control" placeholder="Search" id="search" name="search"/>
						        <div class="input-group-btn">
						          <button class="btn btn-primary" type="submit">
						            <span class="glyphicon glyphicon-search"></span>
						          </button>
						        </div>
						      </div>
						      
						      
						    </div>
						    
						   <div class="col-xs-3 col-md-3 col-lg-3 col-sm-3"></div> <!-- for alignment purposes -->
					    
					  </div>
					</form>
					
				</div>
				
				<div class="myContainer">
				
					<%if (request.getAttribute("msg") != null){%>
						<div class="alert alert-success">
							<%=request.getAttribute("msg")%>
						</div>
					<%} %>
					
					<% if ( request.getAttribute( "connectionCompleted" )!= null ) { %>
							<h3 style="font-family:sansserif;">Το αίτημα σύνδεσης ολοκληρώθηκε</h3>
					<% } %>
					
					
					<% if ( request.getAttribute( "getUsers" ).equals("friends") ) { %>
						<h2 style="font-family:sansserif;font-weight: bold;">Συνδεδεμένοι με εσάς επαγγελματίες</h2>
	
					<%} 
					else if ( request.getAttribute( "getUsers" ).equals("noFriends") ) { %>
						<h2 style="font-family:sansserif;font-weight: bold;">Δεν έχετε συνδεθεί με κάποιον επαγγελματία</h2>
					<% }
					else if ( request.getAttribute( "getUsers" ).equals("noUsersFromSearch") ){%>
						<h2 style="font-family:sansserif;font-weight: bold;">Δεν βρέθηκαν χρήστες</h2>
					<%}
					else  if ( request.getAttribute( "getUsers" ).equals("usersFromSearch") ){%>	
							<h2 style="font-family:sansserif;font-weight: bold;">Αποτελέσματα Αναζήτησης</h2>
					<%} %>
				
				
					<c:choose>
						<c:when test="${requestScope['getUsers'] != 'usersFromSearch'}">
							<c:set var="count" value="0"/>
							<c:forEach items="${users}" var="user">
								<c:if test="${count==3}">
									</div>						<!-- close row div -->
								   <c:set var="count" value="0"/>
								</c:if>
								<c:if test="${count==0}">
								   <div class="row myRow">
								</c:if>
								
								<!-- create column -->
								<a href="${pageContext.request.contextPath}/jsp_files/publicProfile.jsp?id=${user.id}">
						    		<div class="col-lg-4 col-md-6 col-sm-12 col-xs-12" >
									    <table class="myTable">
									    	<tr>
										    	<td rowspan="3"><img  class="img-circle profileImage" src="<c:out value="${user.photoURL}" />"></td>
										    	<td class="nameSurname"><c:out value="${user.name}" /> <c:out value="${user.surname}" /></td>
										    </tr>
										    <tr>
										    	<td class="deco-none">
										    		<c:choose>
											    		<c:when test="${empty user.workPos}">Επαγγελματική Θέση: <em>Δεν έχει οριστεί.</em></c:when>
											    		<c:otherwise>Επαγγελματική Θέση: <c:out value="${user.workPos}" /></c:otherwise>
											    	</c:choose>
										    	</td>
										    </tr>
										    <tr>
										    	<td class="deco-none">
										    		<c:choose>
											    		<c:when test="${empty user.institution}">Φορέας Απασχόλησης: <em>Δεν έχει οριστεί.</em></c:when>
											    		<c:otherwise>Φορέας Απασχόλησης: <c:out value="${user.institution}" /></c:otherwise>
											    	</c:choose>
										    	</td>
										    </tr>
									    </table>  
								    </div>
							    </a>
							    <c:set var="count" value="${count+1}"/>            
			    			</c:forEach>
					    </c:when>
					    
					    <c:otherwise>
						    
						    <c:forEach items="${users}" var="user">
								<div class="row">
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
									
										<c:choose>
											<c:when test="${user.id!=sessionScope.id}">
												<c:if test="${user.isConnected==0}">
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
										    	<c:if test="${user.isConnected==1}">
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
															<c:if test="${user.isConnected==0}">
																<c:choose>
																	<c:when test="${user.isPending==1}">
															    		<c:choose>
																			<c:when test="${user.sentConnectionRequest==1}"> <!-- the other user sent the request -->
																	    		<td rowspan="3">
																	    		<div class="buttonClass">
																					<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
																						<input type="hidden" name="id" value="${user.id}">
																						<input type="hidden" name="pending" value="${user.isPending}">
																					    <input type="submit" name="rejectButton" value="Απόρριψη αιτήματος" class="btn btn-primary btn-sm reject-button"/>
																					    <input type="submit" name="acceptButton" value="Αποδοχή αιτήματος"  class="btn btn-primary btn-sm accept-button"/>
																					</form>
																				</div>
																	    		</td>
																			</c:when>
																			<c:otherwise>
																	    		<td rowspan="3">
																	    			<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
																						<input type="hidden" name="id" value="${user.id}">
																						<input type="hidden" name="pending" value="${user.isPending}">
																					    <input type="submit" name="rejectButton" value="Ακύρωση αιτήματος" class="btn btn-primary reject-button"/>
																					</form>
																	    		</td>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<td rowspan="2">
															    			<form action="${pageContext.request.contextPath}/Network" method="POST">
															    				<input type="hidden" name="userId" value="${user.id}" />
																			    <input class="btn btn-primary" type="submit" name="connect" value="Σύνδεση" />
																			</form>
																		</td>
																	</c:otherwise>
																</c:choose>
													    	</c:if>
													    	<c:if test="${user.isConnected==1}">
													    		<td rowspan="3">
													    			<form action="${pageContext.request.contextPath}/PrivateProfile" method="POST">
																		<input type="hidden" name="id" value="${user.id}">
																		<input type="hidden" name="pending" value="${user.isPending}">
																	    <input type="submit" name="rejectButton" value="Διαγραφή Σύνδεσης" class="btn btn-primary reject-button"/>
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
												    	</c:if>
												    	<c:if test="${user.isConnected==1}">
												    		 <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.workPos}">Επαγγελματική Θέση: <em>Δεν έχει οριστεί.</em></c:when>
															    		<c:otherwise>Επαγγελματική Θέση: <c:out value="${user.workPos}" /></c:otherwise>
															    	</c:choose>
																</td>
														    </tr>
														    <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.institution}">Φορέας Απασχόλησης: <em>Δεν έχει οριστεί.</em></c:when>
															    		<c:otherwise>Φορέας Απασχόλησης: <c:out value="${user.institution}" /></c:otherwise>
															    	</c:choose>
																</td>
														    	
														    </tr>
													    </c:if>
													</c:when>
													<c:otherwise>
														    <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.workPos}">Επαγγελματική Θέση: <em>Δεν έχει οριστεί.</em></c:when>
															    		<c:otherwise>Επαγγελματική Θέση: <c:out value="${user.workPos}" /></c:otherwise>
															    	</c:choose>
																</td>
														    </tr>
														    <tr>
														    	<td class="deco-none">
														    		<c:choose>
															    		<c:when test="${empty user.institution}">Φορέας Απασχόλησης: <em>Δεν έχει οριστεί.</em></c:when>
															    		<c:otherwise>Φορέας Απασχόλησης: <c:out value="${user.institution}" /></c:otherwise>
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
						    
						    
					    </c:otherwise>
						
					</c:choose>
					
				
				</div> <!-- myContainer -->
				
		    </div> <!-- container -->
		</div>	<!-- main -->
		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>