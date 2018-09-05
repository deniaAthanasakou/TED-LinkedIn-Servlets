<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/admin_page.css" type="text/css">
		<script src="${pageContext.request.contextPath}/js_files/actionCheckboxes.js"></script>
		<script src="${pageContext.request.contextPath}/js_files/submitNestedForm.js"></script>
		<title>Admin Page</title>
	</head>
	<body>
	
		<% if ( request.getAttribute( "redirectList" ) == null ) { %>
			<jsp:forward page="/ListUsers?action=getUsers" />
		<% } %>
		
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="list_users">
				<h2 id="titleList">Λίστα χρηστών</h2>
				<div class="action_checkboxes btn-group">
					<button id="deselectAll" type="button" class="btn btn-default btn-sm" onclick="uncheckAll();"><i class="glyphicon glyphicon-remove-circle"></i> Deselect All</button>
  					<button id="selectAll" type="button" class="btn btn-default btn-sm" onclick="checkAll();"><i class="glyphicon glyphicon-ok-circle"></i> Select All</button>
				</div>
				<table class="table table-hover">
					<thead>
					    <tr>
						    <th class="text-center">Επιλογή</th>
						    <th class="text-center">Εικόνα</th>
						    <th class="text-center">Όνομα</th>
						    <th class="text-center">Επώνυμο</th>
						    <th class="text-center">Προφίλ</th>
					    </tr>
				    </thead>
				    <tbody>
				    <c:forEach items="${users}" var="user">
				    	<c:if test="${user.id > 1 }">
				    		<tr>
						    	<td class="text-center">
							    	<div class="checkbox">
										<label><input class="check_item" type="checkbox" value=""></label>
									</div>
								</td>
						        <td class="text-center">
						        	<img class="image_circle" alt="thumbnail" src="${user.photoURL}" style="width:40px;height:40px">
								</td>
						        <td class="text-center">${user.name}</td>
						        <td class="text-center">${user.surname}</td>
						        <td class="text-center">
						        	<form id="getProfile${user.id}" role="Form" method="post" action="${pageContext.request.contextPath}/Profile?fromAdmin=${user.id}" accept-charset="UTF-8">
						        		<button type="button" onclick="submitProfile(${user.id})" class="btn btn-primary btn-md">Προφίλ <i class="glyphicon glyphicon-chevron-right"></i></button>
						        	</form>							        
						        </td>
							</tr>
						</c:if>
					</c:forEach>
					</tbody>
				</table>
				<button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-download-alt"></i> Κατέβασμα XMLs</button>
			</div>
		</div>	<!-- main -->
		
		<jsp:include page="Footer.jsp"/>
	</body>
</html>