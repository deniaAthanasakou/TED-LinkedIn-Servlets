<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/main_css.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/user_home.css" type="text/css">
		
		<script src="${pageContext.request.contextPath}/js_files/chooseInputs.js"></script>
		<script src="${pageContext.request.contextPath}/js_files/enableLoading.js"></script>
		
		<title>Home of user</title>
		
	</head>
	<body>
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="leftdiv">
				<a href="${pageContext.request.contextPath}/jsp_files/profile.jsp">
					<div class="item_profile">
						<div>
							<img class="image_circle" alt="thumbnail" src="<%=session.getAttribute("image")%>" style="width:80px;height:80px">
						</div>
						<h4><%=session.getAttribute("name")%> <%=session.getAttribute("surname")%></h4>
					</div>
				</a>
				<a href="${pageContext.request.contextPath}/jsp_files/network.jsp">
					<div class="item_network">
						<p id="connections_number">47</p>
						<p>Connections</p>
					</div>
				</a>
			</div>
			
			<div class="create_post">
				<div class="info_post" id="info_post">
					<img class="image_circle_view" alt="thumbnail" src="<%=session.getAttribute("image")%>">
					<h5><b><%=session.getAttribute("name")%> <%=session.getAttribute("surname")%></b></h5>
				</div>
				<div class="loader" id="loader"></div>
				<div class="form_post" id="form_post">
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/PostCreation" accept-charset="UTF-8" enctype="multipart/form-data" onsubmit="return enableLoading()">
						<div class="form-group divider">
						    <textarea id="text_post" name="text_post" placeholder="Share a photo, video, audio or idea" rows="3" cols="50"></textarea>
					  	</div>
						
						<div style="display:inline-block;">
							<div style="height:0px;width:0px;overflow:hidden;">
								<input type="file" id="inputImages" accept="image/*" name ="imagesUpload" multiple/>
							</div>
					        <button type="button" class="btn btn-secondary" onclick="chooseImagesInput()"><i class="glyphicon glyphicon-camera"></i> Images</button>            
				        </div>
				        <div style="display:inline-block;">
					        <div style="height:0px;width:0px;overflow:hidden">
								<input type="file" id="inputVideo" accept="video/*" name ="videoUpload" multiple/>
							</div>           
					        <button type="button" class="btn btn-secondary" onclick="chooseVideoInput()"><i class="glyphicon glyphicon-facetime-video"></i> Video</button>
						</div>
						<div style="display:inline-block;">
						  	<div style="height:0px;width:0px;overflow:hidden">
								<input type="file" id="inputAudio" accept="audio/*" name ="audioUpload" multiple/>
							</div> 
						  	<button type="button" class="btn btn-secondary" onclick="chooseAudioInput()"><i class="glyphicon glyphicon-music"></i> Audio</button>
					  	</div>
					  	<button class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-ok"></i> Post</button>
					</form>
				</div>
			</div>
			
			<div class="posts">
				<c:forEach items="${posts}" var="post"> 
					<c:set var="post" value="${post}" scope="request"/>
					<c:import url="PostItem.jsp"/>
				</c:forEach>
			</div>
		</div>
		
		<jsp:include page="Footer.jsp"/>
	
	</body>
</html>