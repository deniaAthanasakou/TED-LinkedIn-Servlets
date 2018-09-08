<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/jobItem.css" type="text/css">
		<script src="${pageContext.request.contextPath}/js_files/showHide.js"></script>
		<title>Job Additional Info</title>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="mainInfoDesc">
				<div class="row">
					<div class="col-md-4">
						<img src="${pageContext.request.contextPath}/images/company-name.png">
					</div>
					<div class="col-md-8">
						<h2>JobTitle</h2>
						<h3>JobIndustry</h3>
						<h5 style="color:#777777">JobLocation</h5>
						<p style="color:#ccc">Date posted</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
					</div>
					<div class="col-md-8">
						<button class="btn btn-primary btn-lg">Αίτηση</button>
					</div>
				</div>
			</div>
			
			<div class="additionalInfo">
				<div class="row" id="switchContent">
					<div class="col-md-9">
						<h3>Περιγραφή Εργασίας</h3>
						<p>MPLA MPLA MPLA</p>
					</div>
					<div class="col-md-3">
						<h4 style="padding-bottom:5px;">Πώς ταιριάζεις</h4>
						<h5 style="text-decoration:underline;">Δεξιότητες</h5>
						<ul style="font-size: 15px;color: gray;">
							<li>First item</li>
							<li>Second item</li>
							<li>Third item</li>
						</ul>
						<h5 style="text-decoration:underline;">Επίπεδο εκπαίδευσης</h5>
						<p style="border-bottom: 1px solid #ccc;padding-bottom:10px;font-size: 15px;">Education</p>
						<h4 style="padding-bottom:5px;">Λεπτομέρειες εργασίας</h4>
						<h5 style="text-decoration:underline;">Είδος εμπειρίας</h5>
						<p style="font-size: 15px;">Education</p>
						<h5 style="text-decoration:underline;">Είδος εταιρείας</h5>
						<p style="font-size: 15px;">Education</p>
						<h5 style="text-decoration:underline;">Τύπος εργασίας</h5>
						<p style="font-size: 15px;">Education</p>
						<h5 style="text-decoration:underline;">Λειτουργίες εργασίας</h5>
						<p style="font-size: 15px;">Education</p>
					</div>
				</div>
				<div class="row show-more">
					<span><input type="button" id="showHide" onclick="displayDesc();" value="See more"> <i id="showHideGlyph" class="glyphicon glyphicon-chevron-down"></i></span>
				</div>
			</div>
		</div>
		
		<jsp:include page="Footer.jsp" />

	</body>
</html>