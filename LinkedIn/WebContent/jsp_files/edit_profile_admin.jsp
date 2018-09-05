<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/profile.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/profileNavBar.css" type="text/css">
			
		<link href="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.js"></script>				

		<script src="${pageContext.request.contextPath}/js_files/handleImage.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/admin_page.css" type="text/css">

		<title>Edit Profile</title>
		
		
	</head>
	<body>
	
	
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">	
				<form role="Form" method="POST" action="${pageContext.request.contextPath}/EditProfile?fromAdmin=${user.id}" accept-charset="UTF-8" enctype="multipart/form-data">
					
					<% if ( request.getAttribute( "editError" ) != null ) { %>
						<div class="alert alert-danger">
							<strong>Error!</strong> <%=request.getAttribute( "editError" )%>
						</div>
					<% } %>
					<%if (request.getAttribute("correctUpdate") != null){%>
						<div class="alert alert-success">
							Your personal information has been updated!
						</div>
					<%} %>
									
				
					<table class="table">
						<tbody>
					    	<tr>
						    	<td rowspan="6" class="imageTd" >
						    	
						    		 <div class="form-group">
						    		 	<c:choose>
								    		<c:when test="${user.hasImage eq 0}">
								    			<label>Προσθήκη φωτογραφίας:</label>
										        <div id="uploadedImageDiv">
										         	<img id="uploadedImage" class="profileImage"/>
										        </div>
								    		</c:when>
								    		<c:otherwise>
								    			<label>Αλλαγή φωτογραφίας:</label>
										        <div id="uploadedImageDiv">
										         	<img id="uploadedImage" class="profileImage" src="${user.photoURL}"/>
										         	<input type="button" value="Remove" onclick="javascript: removeImageEdit();" class="remove" />
										         	<input type="hidden" name="removedImage" id="removedImage" value="">
										        </div>
								    		</c:otherwise>
								    	</c:choose>
								        <div class="input-group browse">
								            <span class="input-group-btn">
								                <span class="btn btn-default btn-file">
								                    Browse… <input type="file" id="imgInp" name ="imgInp" onchange="readURL(this);" />
								                </span>
								            </span>
								          
								        </div>
								    </div>
						    	
						    	</td>
						    	<td class="nameSurname">
						    		<div class="row">
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 form-group ">
											<label for="name">Όνομα:</label>
											<input type="text" name="name" placeholder="Name..." class="form-control " value="${user.name}" required>
										</div>
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 form-group ">
											<label for="surname">Επώνυμο:</label>
											<input type="text" name="surname" placeholder="Surname..." class="form-control" value="${user.surname}" required>
										</div>
									</div>
								</td>
						    </tr>
						    <tr>
						    	<td><label data-toggle="tooltip" title="Μπορείτε να το αλλάξετε στις ρυθμισεις.">Email:</label> <c:out value="${user.email}"/></td>
						    	<script>
								$(document).ready(function(){
								    $('[data-toggle="tooltip"]').tooltip();   
								});
								</script>
						    </tr>
						    <tr>
						    	<td>
									<div class="form-group">
										<label for="telephone">Τηλέφωνο:</label>
										<c:choose>
								    		<c:when test="${empty user.tel}"><input type="tel" name="telephone" placeholder="Telephone..." class="form-control"></c:when>
								    		<c:otherwise><input type="tel" name="telephone" placeholder="Telephone..." class="form-control" value="${user.tel}"></c:otherwise>
								    	</c:choose>
										
									</div>
								</td>
						    </tr>
						    <tr>
						    	<td>
						    	
						    		<div class="row">
						    			<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
				    						<div class="form-group">
									       		<label class="control-label" for="people">Ημέρα:</label>
									      		<select class="form-control" id="day" name="day">
									      			<c:forEach begin="1" end="31" varStatus="loop">														
														<c:choose>
												    		<c:when test="${loop.index eq day}"><option value="${loop.index}" selected="selected">${loop.index}</option></c:when>
												    		<c:otherwise><option value="${loop.index}">${loop.index}</option></c:otherwise>
												    	</c:choose>
														
													</c:forEach>

											    </select>
										  	</div>
									  	</div>
									  	<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
										  	<div class="form-group">
									       		<label class="control-label" for="people">Μήνας:</label>
									      		<select class="form-control" id="month" name="month">
									      			<c:forEach begin="1" end="12" varStatus="loop">
														<c:choose>
												    		<c:when test="${loop.index eq month}"><option value="${loop.index}" selected="selected">${loop.index}</option></c:when>
												    		<c:otherwise><option value="${loop.index}">${loop.index}</option></c:otherwise>
												    	</c:choose>
													</c:forEach>


											    </select>
										  	</div>
									  	</div>
									  	<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
										  	<div class="form-group">
									       		<label class="control-label" for="people">Έτος:</label>
										      		<select class="form-control" id="year" name="year">
										      			<c:forEach begin="1900" end="2018" varStatus="loop">
										      				<c:set var="i" value="${2018-loop.index+ 1900}" scope="page"></c:set>
										      				<c:choose>
													    		<c:when test="${i eq year}"><option value="${i}" selected="selected">${i}</option></c:when>
													    		<c:otherwise><option value="${i}">${i}</option></c:otherwise>
													    	</c:choose>
														</c:forEach>
												    </select>
										  	</div>
									  	</div>
								  	</div>
      
								</td>
						    </tr>
						    <tr>
						    	<td>
						    		<div class="form-group">
							    		<label for="gender">Φύλο:</label>
										<div class="radio">
										  <label>
										  	<c:choose>
									    		<c:when test="${user.gender == 1}">
									    			<input type="radio" name="gender" value="male" checked>Άνδρας
									    		</c:when>
									    		<c:otherwise><input type="radio" name="gender" value="male">Άνδρας</c:otherwise>	
									    	</c:choose>
										  </label>
										</div>
										<div class="radio">
										  <label>
										  	<c:choose>
									    		<c:when test="${user.gender == 2}">
									    			<input type="radio" name="gender"  value="female" checked>Γυναίκα
									    		</c:when>
									    		<c:otherwise><input type="radio" name="gender" value="female">Γυναίκα</c:otherwise>	
									    	</c:choose>
										  </label>
										</div>
									</div>
								</td>
						    </tr>
						    <tr>
						    	<td>
						    		<div class="row">
							    		<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 form-group ">
							    			<script src="${pageContext.request.contextPath}/js_files/countrypicker.min.js"></script>
								    		<label class="gds-countryflag">Χώρα κατοικίας:</label>
											 <c:choose>
										    		<c:when test="${empty user.country}"> <select class="form-control selectpicker countrypicker" name="country" data-live-search="true" data-default="Greece" data-flag="true"></select></c:when>
										    		<c:otherwise> <select class="form-control selectpicker countrypicker" name="country" data-live-search="true" data-default="${user.country}" data-flag="true"></select></c:otherwise>
										     </c:choose>
										</div>
												
										
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 form-group ">
							    			<label class="gds-countryflag">Πόλη/Περιοχή κατοικίας:</label>
											<c:choose>
										    		<c:when test="${empty user.city}"><input type="text" name="city" placeholder="City..." class="form-control"></c:when>
										    		<c:otherwise> <input type="text" name="city" placeholder="City..." class="form-control" value="${user.city}"></c:otherwise>
										     </c:choose>
										</div>
									</div>
									
									
								</td>
						    </tr>
					    </tbody>
					 </table>
					 <div class="info">
					 	<div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="workPos">Εισάγετε πληροφορίες σχετικές με την επαγγελματική θέση σας:</label>
								  <c:choose>
							    		<c:when test="${empty  user.workPos}"><textarea class="form-control" rows="3" id="workPos" name="workPos" placeholder="Job..."></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="3" id="workPos" name="workPos" placeholder="Job..."><c:out value="${user.workPos}"/></textarea></c:otherwise>
							     </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="workPos">Εισάγετε πληροφορίες σχετικές με τον φορέα απασχόλησής σας:</label>
								  <c:choose>
							    		<c:when test="${empty  user.institution}"><textarea class="form-control" rows="3" id="institution" name="institution" placeholder="Institution..."></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="3" id="institution" name="institution" placeholder="Institution..."><c:out value="${user.institution}"/></textarea></c:otherwise>
							     </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="education">Εισάγετε πληροφορίες σχετικές με την επαγγελματική εμπειρία σας:</label>
								  <c:choose>
							    		<c:when test="${empty  user.profExp}"><textarea class="form-control" rows="10" id="work" name="work" placeholder="Professional experience..."></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="10" id="work" name="work" placeholder="Professional experience..."><c:out value="${user.profExp}"/></textarea></c:otherwise>
							     </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="education">Εισάγετε πληροφορίες σχετικές με την εκπαίδευσή σας:</label>
								  <c:choose>
							    		<c:when test="${empty  user.education}"><textarea class="form-control" rows="10" id="education" name="education" placeholder="Education..."></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="10" id="education" name="education" placeholder="Education..." ><c:out value="${user.education}"/></textarea></c:otherwise>
							      </c:choose>
							</div>
						 </div>
						  <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="education">Εισάγετε πληροφορίες σχετικές με τις δεξιότητές σας:</label>
								  <c:choose>
							    		<c:when test="${empty  user.skills}"><textarea class="form-control" rows="10" id="skills" name="skills" placeholder="Skills..."></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="10" id="skills" name="skills" placeholder="Skills..."><c:out value="${user.skills}"/></textarea></c:otherwise>
							      </c:choose>
							</div>
						 </div>
						
					</div>
					
					<div class="choosePrivate">
						<label for="checkbox">Επιλέξτε ποιες πληροφορίες σας θα είναι ιδιωτικές:</label>
						<div class="checkbox disabled">
						  <label><input type="checkbox" disabled>Ονοματεπώνυμο</label>
						</div>
						<div class="checkbox">
							<label>
							<c:choose>
					    		<c:when test="${user.privateEmail==1}"><input type="checkbox" value="pr_email" name="pr_email" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_email" name="pr_email"></c:otherwise>
					    	</c:choose>
						    Ηλεκτρονική διέυθυνση/Email</label>
						</div>
						<div class="checkbox ">
						  <label>
						  	<c:choose>
					    		<c:when test="${user.privateTelephone==1}"> <input type="checkbox" value="pr_telephone" name="pr_telephone" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_telephone" name="pr_telephone"></c:otherwise>
					    	</c:choose>
						  Τηλέφωνο</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateDateOfBirth==1}"><input type="checkbox" value="pr_dateOfBirth" name="pr_dateOfBirth" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_dateOfBirth" name="pr_dateOfBirth"></c:otherwise>
					     </c:choose>
						  Ημερομηνία γέννησης</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateGender==1}"> <input type="checkbox" value="pr_gender" name="pr_gender" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_gender" name="pr_gender"></c:otherwise>
					     </c:choose>
						  Φύλο</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateCountry==1}"><input type="checkbox" value="pr_country" name="pr_country" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_country" name="pr_country"></c:otherwise>
					     </c:choose>
						  Χώρα κατοικίας</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateCity==1}"> <input type="checkbox" value="pr_city" name="pr_city" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_city" name="pr_city"></c:otherwise>
					     </c:choose>
						  Πόλη/Περιοχή κατοικίας</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateWorkPos==1}"> <input type="checkbox" value="pr_workPos" name="pr_workPos" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_workPos" name="pr_workPos"></c:otherwise>
					     </c:choose>
						  Επαγγελματική Θέση</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateInstitution==1}"> <input type="checkbox" value="pr_institution" name="pr_institution" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_institution" name="pr_institution"></c:otherwise>
					     </c:choose>
						  Φορέας Απασχόλησης</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateProfExp==1}"> <input type="checkbox" value="pr_profExp" name="pr_profExp" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_profExp" name="pr_profExp"></c:otherwise>
					     </c:choose>
						  Επαγγελματική Εμπειρία</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateEducation==1}"> <input type="checkbox" value="pr_education" name="pr_education" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_education" name="pr_education"></c:otherwise>
					     </c:choose>
						  Εκπαίδευση</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateSkills==1}"> <input type="checkbox" value="pr_skills" name="pr_skills" checked></c:when>
					    		<c:otherwise><input type="checkbox"value="pr_skills" name="pr_skills"></c:otherwise>
					     </c:choose>
						  Δεξιότητες</label>
						</div>
					</div>
					
					 <div class="form-group row buttons">
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
							 	<input type="reset" class="btn cancel" value="Go to Admin Page" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/admin_page.jsp';">
							</div>
							
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4"></div>
							
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
							 	<button type="submit" class="btn btn-primary submit">Submit</button>
							</div>
								
					 </div>
					
					
					 
				  </form>
			
				
			</div>
		</div>		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>