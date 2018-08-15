<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="../css_files/main_css.css" type="text/css">
		<link rel="stylesheet" href="../css_files/user_home.css" type="text/css">
		
		<script src="../js_files/chooseInputs.js"></script>
		
		<title>Home of user</title>
		
	</head>
	<body>
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="leftdiv">
				<a href="./profile.jsp">
					<div class="item_profile">
						<div>
							<img class="image_circle" alt="thumbnail" src="<%=request.getAttribute("image")%>" style="width:80px;height:80px">
						</div>
						<h4><%=request.getAttribute("name")%> <%=request.getAttribute("surname")%></h4>
					</div>
				</a>
				<a href="./jsp_files/network.jsp">
					<div class="item_network">
						<p id="connections_number">47</p>
						<p>Connections</p>
					</div>
				</a>
			</div>
			
			<div class="create_post">
				<div class="info_post">
					<img class="image_circle_view" alt="thumbnail" src="<%=request.getAttribute("image")%>">
					<h5><b><%=request.getAttribute("name")%> <%=request.getAttribute("surname")%></b></h5>
				</div>
				<div class="form_post">
					<form>
						<div class="form-group divider">
						    <textarea id="text_post" placeholder="Share a photo, video, audio or idea" rows="3" cols="50"></textarea>
					  	</div>
						
						<div style="display:inline-block;">
							<div style="height:0px;width:0px;overflow:hidden;">
								<input type="file" id="inputImages" accept="image/*" name ="imagesUpload"/>
							</div>
					        <button type="button" class="btn btn-secondary" onclick="chooseImagesInput()"><i class="glyphicon glyphicon-camera"></i> Images</button>            
				        </div>
				        <div style="display:inline-block;">
					        <div style="height:0px;width:0px;overflow:hidden">
								<input type="file" id="inputVideo" accept="video/*" name ="videoUpload"/>
							</div>           
					        <button type="button" class="btn btn-secondary" onclick="chooseVideoInput()"><i class="glyphicon glyphicon-facetime-video"></i> Video</button>
						</div>
						<div style="display:inline-block;">
						  	<div style="height:0px;width:0px;overflow:hidden">
								<input type="file" id="inputAudio" accept="audio/*" name ="audioUpload"/>
							</div> 
						  	<button type="button" class="btn btn-secondary" onclick="chooseAudioInput()"><i class="glyphicon glyphicon-music"></i> Audio</button>
					  	</div>
					  	<button class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-ok"></i> Post</button>
					</form>
				</div>
			</div>
			
			<div class="posts">
			
			</div>
		</div>
		
		<jsp:include page="Footer.jsp"/>
	
	</body>
</html>