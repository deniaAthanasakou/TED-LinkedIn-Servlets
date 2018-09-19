<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
			<!-- custom -->
			
			<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/messaging.css" type="text/css">
			<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
			
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Messaging</title>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<c:if test="${requestScope.getSpecific == null}">
		<% if ( request.getAttribute( "redirect" ) == null ) { %>
			<jsp:forward page="/MessageHandler?action=getMessages&user1=${conversation.id.userId1}&user2=${conversation.id.userId2}" />
		<% } %>
		</c:if>
		
		<div class="main">
			<div class="container">
				<div class="myContainer">
					<h3 class=" text-center">Messaging</h3>
					<br>
					<div class="row">
				    	<div class="card col-xs-4 col-sm-4 col-md-4 col-lg-4 otherPeopleMessages" style="overflow:auto;">
				       		<ul class="list-group list-group-flush">
				         		<c:forEach items="${conversations}" var="conversationItem" varStatus="status">
				         			<c:choose>
					         			<c:when test="${status.index == pressedConversation}">
						            		<li class="list-group-item" style="background-color: #C0C0C0;" onclick="window.location.href='${pageContext.request.contextPath}/MessageHandler?action=getMessages&user1=${conversationItem.id.userId1}&user2=${conversationItem.id.userId2}'">
							            		<div class="row">
								            		<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
										              <img  class="img-circle profileImage" src="${conversationItem.photoURL}">
										            </div>
										            <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
										              <div class="d-flex w-100 justify-content-between">
										                    <h5 class="mb-1" style="text-align:center">${conversationItem.name} ${conversationItem.surname}</h5>
										                    <h5 class="mb-1 datePostedHim" style="text-align:center">${conversationItem.dateInterval} ago</h5>
										                </div>
										            </div>
									        	</div>
							            		<br>
							            		<c:set var="length" value = "${fn:length(conversationItem.messages)}"/>				            	
							                	<p class="lastMsg">${conversationItem.messages[length-1].text}</p>
							            	</li>
						            	</c:when>
					            		<c:otherwise>
						            		<li class="list-group-item" onclick="window.location.href='${pageContext.request.contextPath}/MessageHandler?action=getMessages&user1=${conversationItem.id.userId1}&user2=${conversationItem.id.userId2}'">
							            		<div class="row">
								            		<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
										              <img  class="img-circle profileImage" src="${conversationItem.photoURL}">
										            </div>
										            <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
										              <div class="d-flex w-100 justify-content-between">
										                    <h5 class="mb-1" style="text-align:center">${conversationItem.name} ${conversationItem.surname}</h5>
										                </div>
										            </div>
									        	</div>
							            		<br>
							            		<c:set var="length" value = "${fn:length(conversationItem.messages)}"/>				            	
							                	<p class="lastMsg">${conversationItem.messages[length-1].text} </p>
							            	</li>
					            		</c:otherwise>
					            	</c:choose>
					            </c:forEach>
				          	</ul>
			        	</div>
			        
				        <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
				        
				          <c:if test="${fn:length(conversation.messages) != 0}">
				          <div class="row chatbody">
				    
				            <ul class="messageList col-xs-12 col-sm-12 col-md-12 col-lg-12">
								 
								 <c:forEach items="${conversation.messages}" var="message">
								 	<c:choose>
								 		<c:when test="${message.sender==0 && message.conversation.id.userId1 == sessionScope.id}">
								 			<c:set var = "messageUser" value = "userId1"/>
									 	</c:when>
									 	<c:when test="${message.sender==1 && message.conversation.id.userId2 == sessionScope.id}">
									 		<c:set var = "messageUser" value = "userId2"/>
									 	</c:when>
									 	<c:otherwise>
									 		<c:set var = "messageUser" value = "null"/>
									 	</c:otherwise>
								 	</c:choose>
							 		<c:if test="${messageUser == 'userId1'}">
							 			<li class="me messageListItem">${message.text}<p class="datePostedMe">${message.dateInterval}</p></li>
						    		</c:if>
						    		<c:if test="${messageUser == 'userId2'}">
							 			<li class="me messageListItem">${message.text}<p class="datePostedMe">${message.dateInterval}</p></li>
						    		</c:if>
							    	<c:if test="${messageUser == 'null'}">
							 			<li class="him messageListItem">${message.text}<p class="datePostedHim">${message.dateInterval}</p></li>
						    		</c:if>	
								 </c:forEach>
							</ul>
				    
				          </div>
				          </c:if>
				
						  <c:if test="${noConversations != null}">
						  	  <div class="alert alert-warning" role="alert">
								  ${noConversations}
							  </div>
					          <div class="row send">
					          	<form action="${pageContext.request.contextPath}/MessageHandler" method="POST">
					          		<input type="hidden" name="userId1" value="${conversation.id.userId1}">
					          		<input type="hidden" name="userId2" value="${conversation.id.userId2}">
						            <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
						              <input type="text" name="message" placeholder="Message..." class="form-control" disabled/>
						            </div>
						            <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
						              <button class="btn btn-info btn-block disabled" type="submit">Send</button>
						            </div>
					            </form>
					          </div>
				          </c:if>
				          <c:if test="${noConversations == null}">
				          	<div class="row send">
					          	<form action="${pageContext.request.contextPath}/MessageHandler" method="POST">
					          		<input type="hidden" name="userId1" value="${conversation.id.userId1}">
					          		<input type="hidden" name="userId2" value="${conversation.id.userId2}">
						            <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
						              <input type="text" name="message" placeholder="Message..." class="form-control" />
						            </div>
						            <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
						              <button class="btn btn-info btn-block" type="submit">Send</button>
						            </div>
					            </form>
					          </div>
				          </c:if>
				
				        </div>
				      </div>
					
					</div>

			</div>
		</div>
		
		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>