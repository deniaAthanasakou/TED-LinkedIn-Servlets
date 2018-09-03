<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/post_item.css" type="text/css">  	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/playlist.css" type="text/css">  			
		<title>PostItem</title>
		<script src="${pageContext.request.contextPath}/js_files/commentSection.js"></script>
		<script src="${pageContext.request.contextPath}/js_files/playlist.js"></script>
		
	</head>
	<body>
		<div class="post">
			<div class="info_post" id="info_post">
				<img class="image_circle_view" alt="thumbnail" src="${post.user.photoURL}">
				<h5><b>${post.user.name} ${post.user.surname}</b></h5>
				<p style="color:#999999;">${requestScope.post.dateInterval}</p>
			</div>
			<!-- text post -->
			<c:if test="${post.text!='null'}">
				<p class="post_text">${requestScope.post.text}</p>
			</c:if>
			<c:if test="${post.hasImages=='1'}">
				<!-- for images,carousel -->
				<div id="myCarouselImages${post.id}" class="carousel slide" data-ride="carousel" data-interval="false">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<c:forEach items="${post.listImages}" var="image" varStatus="stat"> 
							<c:if test="${stat.first}">
								<li data-target="#myCarouselImages${post.id}" data-slide-to="${stat.index}" class="active"></li>
						    </c:if>
						    <c:if test="${!stat.first}">
						        <li data-target="#myCarouselImages${post.id}" data-slide-to="${stat.index}"></li>
						    </c:if>
						</c:forEach>
					</ol>
					
					<!-- Wrapper for slides -->
					<div class="carousel-inner">
						<c:forEach items="${post.listImages}" var="image" varStatus="stat"> 
							<c:if test="${stat.first}">
								<div class="item active">
									<img src="${image}" style="width:100%;">
								</div>
						    </c:if>
						    <c:if test="${!stat.first}">
						        <div class="item">
									<img src="${image}" style="width:100%;">
								</div>
						    </c:if>
						</c:forEach>
					</div>
					
					<!-- Left and right controls -->
					<a class="left carousel-control" href="#myCarouselImages${post.id}" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#myCarouselImages${post.id}" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</c:if>
			<c:if test="${post.hasVideos=='1'}">
				<!-- for videos,carousel -->
				<div id="myCarouselVideos${post.id}" class="carousel slide" data-ride="carousel" data-interval="false" style="margin-top:5px;">
					
					<!-- Wrapper for slides -->
					<div class="carousel-inner">
						<c:forEach items="${post.listVideos}" var="video" varStatus="stat"> 
							<c:if test="${stat.first}">
								<div class="item active">
									<video width="100%" height="200" controls>
										<source src="${video}" type="video/mp4">
									</video>
								</div>
						    </c:if>
						    <c:if test="${!stat.first}">
						        <div class="item">
									<video width="100%" height="200" controls>
										<source src="${video}" type="video/mp4">
									</video>
								</div>
						    </c:if>
						</c:forEach>
					</div>
					
					<!-- Left and right controls -->
					<a class="left carousel-control" href="#myCarouselVideos${post.id}" data-slide="prev" role="button">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#myCarouselVideos${post.id}" data-slide="next" role="button">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</c:if>
			<c:if test="${post.hasAudio=='1'}">
				<audio id="audio" preload="auto" tabindex="0" controls="" type="audio/mpeg">
			        <source type="audio/mp3" src="${post.listAudios[0]}">
			        Sorry, your browser does not support HTML5 audio.
			    </audio>
			    <ul id="playlist">
			    	<c:forEach items="${post.listAudios}" var="audio" varStatus="stat"> 
			    		<c:if test="${stat.first}">
			    			<li class="active"><a class="track" href="${audio}">${post.listAudiosNames[stat.index]}</a></li>
			    		</c:if>
			    		<c:if test="${!stat.first}">
			    			<li><a class="track" href="${audio}">${post.listAudiosNames[stat.index]}</a></li>
			    		</c:if>
					</c:forEach>
			    </ul>
			</c:if>
			<div class="metrics">
				<p class="post_text">${post.likes} Likes &middot;</p>
				<p class="post_text">${post.noComments} Comments</p>
			</div>
			<div class="button_actions">
				<c:if test="${post.liked == '0'}">
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/PostHandle?action=insertLike" accept-charset="UTF-8">
					    <button id="giveLike" class="btn btn-default"  type="submit"><i class="glyphicon glyphicon-thumbs-up"></i> Like</button>   
					    <input type="hidden" name="post_id" value="${post.id}" />         
						<button id="makeComment" type="button" class="btn btn-default" onclick="enableCommentsSection(${post.id})"><i class="glyphicon glyphicon-comment"></i> Comment</button>
					</form>
				</c:if>
				<c:if test="${post.liked == '1'}">
					<form role="Form" method="POST" action="${pageContext.request.contextPath}/PostHandle?action=deleteLike" accept-charset="UTF-8">
					    <button id="returnLike" class="btn btn-default active"  type="submit"><i class="glyphicon glyphicon-thumbs-up"></i> Like</button>   
					    <input type="hidden" name="post_id" value="${post.id}" />         
						<button id="makeComment" type="button" class="btn btn-default" onclick="enableCommentsSection(${post.id})"><i class="glyphicon glyphicon-comment"></i> Comment</button>
					</form>
				</c:if>
				
			</div>
			
		
			<div class="commentsBox" id="commentsBox" style="display:none;">
				<% if ( request.getAttribute( "commentError" ) != null ) { %>
					<div class="alert alert-danger">
						<strong>Error!</strong> <%=request.getAttribute( "commentError" )%>
					</div>
				<% } %>
				<div class="commenterImage">
	            	<img src="<%=session.getAttribute("image")%>" />
	            </div>
		        <form class="form-inline" role="form" method="POST" id="commentForm" action="${pageContext.request.contextPath}/CommentCreation" accept-charset="UTF-8">
		            <div class="form-group">
		                <input class="form-control" id="comment" name="comment" type="text" placeholder="Add a comment..." />
		                 <input type="hidden" name="post_id" value="${post.id}" />
		            </div>
		        </form>
		        <ul class="commentList">
			        <c:forEach items="${post.comments}" var="comment"> 
						<li>
			                <div class="commenterImage">
			                  <img src="${comment.user.photoURL}" />
			                </div>
			                <div class="commentText">
			                    <p class="">${comment.text}</p> <span class="date sub-text">${comment.dateInterval}</span>
			                </div>
		            	</li>
					</c:forEach>
        		</ul>

    		</div>
			
			
		</div>
	</body>
</html>