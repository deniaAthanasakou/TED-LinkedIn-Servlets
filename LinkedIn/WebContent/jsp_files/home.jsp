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
		
		<title>Home</title>
		
	</head>
	<body>
		<c:if test="${requestScope.redirectPosts == null}">
			<jsp:forward page="/PostHandle?action=getPosts" />
		</c:if>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="leftdiv">
				<div class="item_profile" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/profile.jsp'">
					<div>
						<img class="image_circle" alt="thumbnail" src="${sessionScope.image}" style="width:80px;height:80px">
					</div>
					<h4>${sessionScope.name} ${sessionScope.surname}</h4>
				</div>
				<div class="item_network"  onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/network.jsp'">
					<p id="connections_number">${requestScope.noConnections}</p>
					<p>Connections</p>
				</div>
			</div>
			
			<div class="create_post">
				<div class="info_post" id="info_post">
					<img class="image_circle_view" alt="thumbnail" src="${sessionScope.image}">
					<h5><b>${sessionScope.name} ${sessionScope.surname}</b></h5>
				</div>
				<div class="loader" id="loader"></div>
				<div class="form_post" id="form_post">
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/PostHandle" accept-charset="UTF-8" enctype="multipart/form-data" onsubmit="return enableLoading()">
						<c:if test="${requestScope.postError != null}">
							<div class="alert alert-danger">
								${requestScope.postError}
							</div>
						</c:if>
						<div class="form-group divider">
						    <textarea id="text_post" name="text_post" placeholder="Share a photo, video, audio or idea" rows="3" cols="50"></textarea>
					  	</div>
						
						<div style="display:inline-block;">
							<div style="height:0px;width:0px;overflow:hidden;">
								<input type="file" id="inputImages" accept="image/png,image/jpg,image/jpeg" name ="imagesUpload" multiple/>
							</div>
					        <button type="button" class="btn btn-secondary" onclick="chooseImagesInput()"><i class="glyphicon glyphicon-camera"></i> Images</button>            
				        </div>
				        <div style="display:inline-block;">
					        <div style="height:0px;width:0px;overflow:hidden">
								<input type="file" id="inputVideo" accept="video/mp4" name ="videoUpload" multiple/>
							</div>           
					        <button type="button" class="btn btn-secondary" onclick="chooseVideoInput()"><i class="glyphicon glyphicon-facetime-video"></i> Video</button>
						</div>
						<div style="display:inline-block;">
						  	<div style="height:0px;width:0px;overflow:hidden">
								<input type="file" id="inputAudio" accept="audio/mp3" name ="audioUpload" multiple/>
							</div> 
						  	<button type="button" class="btn btn-secondary" onclick="chooseAudioInput()"><i class="glyphicon glyphicon-music"></i> Audio</button>
					  	</div>
					  	<button class="btn btn-danger" type="reset"><i class="glyphicon glyphicon-remove"></i> Reset</button>
					  	<button class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-ok"></i> Post</button>
					</form>
				</div>
				<p id="info_text">Image format: .png, .jpg, .jpeg. Video format: .mp4. Audio format: .mp3.
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