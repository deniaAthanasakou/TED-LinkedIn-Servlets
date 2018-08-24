<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/post_item.css" type="text/css">  		
		<title>PostItem</title>
	</head>
	<body>
		<div class="post">
			<div class="info_post" id="info_post">
				<img class="image_circle_view" alt="thumbnail" src="<%=session.getAttribute("image")%>">
				<h5><b><%=session.getAttribute("name")%> <%=session.getAttribute("surname")%></b></h5>
				<p style="color:#999999;">${requestScope.post.datePosted}</p>
			</div>
			<!-- text post -->
			<c:if test="${post.text!='null'}">
				<p class="post_text">${requestScope.post.text}</p>
			</c:if>
			<c:if test="${post.hasImages=='1'}">
				<!-- for images,carousel -->
				<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>
					
					<!-- Wrapper for slides -->
					<div class="carousel-inner">
						<div class="item active">
							<img src="${pageContext.request.contextPath}/images/default-user.png" alt="Los Angeles" style="width:100%;">
						</div>
						
						<div class="item">
							<img src="${pageContext.request.contextPath}/images/default-user.png" alt="Chicago" style="width:100%;">
						</div>
						
						<div class="item">
							<img src="${pageContext.request.contextPath}/images/default-user.png" alt="New york" style="width:100%;">
						</div>
					</div>
					
					<!-- Left and right controls -->
					<a class="left carousel-control" href="#myCarousel" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#myCarousel" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</c:if>
			<c:if test="${post.hasVideos=='1'}">
				<!-- for videos,carousel -->
				<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>
					
					<!-- Wrapper for slides -->
					<div class="carousel-inner">
						<div class="item active">
							<video width="100%" height="200" controls>
								<source src="movie.mp4" type="video/mp4">
							</video>
						</div>
						
						<div class="item">
							<video width="100%" height="200" controls>
								<source src="movie.mp4" type="video/mp4">
							</video>
						</div>
						
						<div class="item">
							<video width="100%" height="200" controls>
								<source src="movie.mp4" type="video/mp4">
							</video>
						</div>
					</div>
					
					<!-- Left and right controls -->
					<a class="left carousel-control" href="#myCarousel" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#myCarousel" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</c:if>
			<c:if test="${post.hasAudio=='1'}">
				<!-- for audio -->
				<audio controls>
					<source src="horse.mp3" type="audio/mpeg">
				</audio>
			</c:if>
			<div class="metrics">
				<p class="post_text">no likes &middot;</p>
				<p class="post_text">no comments</p>
			</div>
			<div class="button_actions">
				<form role="Form" method="POST" action="${pageContext.request.contextPath}/PostAction" accept-charset="UTF-8" enctype="multipart/form-data">
				    <button id="giveLike" type="button" class="btn btn-default"  type="submit"><i class="glyphicon glyphicon-thumbs-up"></i> Like</button>             
				    <button id="makeComment" type="button" class="btn btn-default"  type="submit"><i class="glyphicon glyphicon-comment"></i> Comment</button>
				</form>
			</div>
			
			<!-- comment section -->
			<div class="comments">
			</div>
		</div>
	</body>
</html>