<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/profile.css" type="text/css">	
		<link href="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/bootstrap-formhelpers/bootstrap-formhelpers.min.js"></script>				
		<script src="${pageContext.request.contextPath}/js_files/handleImage.js"></script>
		<title>Edit Profile</title>

	</head>
	<body>
		<c:import url="Header.jsp" /> 
		
		<div class="main">
			<div class="container">	
				<form role="Form" method="POST" action="${pageContext.request.contextPath}/EditProfile?fromAdmin=${user.id}" accept-charset="UTF-8" enctype="multipart/form-data">
					
					<c:if test="${requestScope.editError != null}">
						<div class="alert alert-danger">
							<strong>Error!</strong> ${requestScope.editError}
						</div>
					</c:if>
					<c:if test="${requestScope.correctUpdate != null}">
						<div class="alert alert-success">
							Your personal information has been updated!
						</div>
					</c:if>									
				
					<table class="table">
						<tbody>
					    	<tr>
						    	<td rowspan="6" class="imageTd" >
						    	
						    		 <div class="form-group">
						    		 	<c:choose>
								    		<c:when test="${user.hasImage eq 0}">
								    			<label>Add a photo:</label>
										        <div id="uploadedImageDiv">
										         	<img id="uploadedImage" class="profileImage"/>
										        </div>
								    		</c:when>
								    		<c:otherwise>
								    			<label>Change photo:</label>
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
											<label for="name">Name:</label>
											<input type="text" name="name" placeholder="Name..." class="form-control " value="${user.name}" maxlength="45" required>
										</div>
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 form-group ">
											<label for="surname">Surname:</label>
											<input type="text" name="surname" placeholder="Surname..." class="form-control" value="${user.surname}" maxlength="50" required>
										</div>
									</div>
								</td>
						    </tr>
						    <tr>
						    	<td><label data-toggle="tooltip" title="You can change this field in Settings">Email:</label> <c:out value="${user.email}"/></td>
						    	<script>
								$(document).ready(function(){
								    $('[data-toggle="tooltip"]').tooltip();   
								});
								</script>
						    </tr>
						    <tr>
						    	<td>
									<div class="form-group">
										<label for="telephone">Telephone:</label>
										<c:choose>
								    		<c:when test="${empty user.tel}"><input type="tel" name="telephone" placeholder="Telephone..." class="form-control" maxlength="15"></c:when>
								    		<c:otherwise><input type="tel" name="telephone" placeholder="Telephone..." class="form-control" value="${user.tel}" maxlength="15"></c:otherwise>
								    	</c:choose>
										
									</div>
								</td>
						    </tr>
						    <tr>
						    	<td>
						    	
						    		<div class="row">
						    			<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
				    						<div class="form-group">
									       		<label class="control-label" for="people">Day:</label>
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
									       		<label class="control-label" for="people">Month:</label>
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
									       		<label class="control-label" for="people">Year:</label>
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
							    		<label for="gender">Gender:</label>
										<div class="radio">
										  <label>
										  	<c:choose>
									    		<c:when test="${user.gender == 1}">
									    			<input type="radio" name="gender" value="male" checked>Male
									    		</c:when>
									    		<c:otherwise><input type="radio" name="gender" value="male">Male</c:otherwise>	
									    	</c:choose>
										  </label>
										</div>
										<div class="radio">
										  <label>
										  	<c:choose>
									    		<c:when test="${user.gender == 2}">
									    			<input type="radio" name="gender"  value="female" checked>Female
									    		</c:when>
									    		<c:otherwise><input type="radio" name="gender" value="female">Female</c:otherwise>	
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
								    		<label class="gds-countryflag">Country of residence:</label>
											 <c:choose>
										    		<c:when test="${empty user.country}"> <select class="form-control selectpicker countrypicker" name="country" data-live-search="true" data-default="Greece" data-flag="true"></select></c:when>
										    		<c:otherwise> <select class="form-control selectpicker countrypicker" name="country" data-live-search="true" data-default="${user.country}" data-flag="true"></select></c:otherwise>
										     </c:choose>
										</div>
												
										
										<div class="col-xs-6 col-md-6 col-lg-6 col-sm-6 form-group ">
							    			<label class="gds-countryflag">City of residence:</label>
											<c:choose>
										    		<c:when test="${empty user.city}"><input type="text" name="city" placeholder="City..." class="form-control" maxlength="100"></c:when>
										    		<c:otherwise> <input type="text" name="city" placeholder="City..." class="form-control" value="${user.city}" maxlength="100"></c:otherwise>
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
								  <label for="workPos">Enter information about your work position:</label>
								  <c:choose>
							    		<c:when test="${empty  user.workPos}"><textarea class="form-control" rows="3" id="workPos" name="workPos" placeholder="Job..." maxlength="3000"></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="3" id="workPos" name="workPos" placeholder="Job..." maxlength="3000"><c:out value="${user.workPos}"/></textarea></c:otherwise>
							     </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="workPos">Enter information about your employment institution:</label>
								  <c:choose>
							    		<c:when test="${empty  user.institution}"><textarea class="form-control" rows="3" id="institution" name="institution" placeholder="Institution..." maxlength="3000"></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="3" id="institution" name="institution" placeholder="Institution..." maxlength="3000"><c:out value="${user.institution}"/></textarea></c:otherwise>
							     </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="education">Enter information about your professional experience:</label>
								  <c:choose>
							    		<c:when test="${empty  user.profExp}"><textarea class="form-control" rows="10" id="work" name="work" placeholder="Professional experience..." maxlength="3000"></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="10" id="work" name="work" placeholder="Professional experience..." maxlength="3000"><c:out value="${user.profExp}"/></textarea></c:otherwise>
							     </c:choose>
							</div>
						 </div>
						 <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="education">Enter information about your education:</label>
								  <c:choose>
							    		<c:when test="${empty  user.education}"><textarea class="form-control" rows="10" id="education" name="education" placeholder="Education..." maxlength="3000"></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="10" id="education" name="education" placeholder="Education..." maxlength="3000"><c:out value="${user.education}"/></textarea></c:otherwise>
							      </c:choose>
							</div>
						 </div>
						  <div class="row">
							 <div class="col-xs-12 col-md-12 col-lg-12 col-sm-12 form-group">
								  <label for="education">Enter information about your skills (Divide with commas):</label>
								  <c:choose>
							    		<c:when test="${empty  user.skills}"><textarea class="form-control" rows="10" id="skills" name="skills" placeholder="Skills..." maxlength="3000"></textarea></c:when>
							    		<c:otherwise> <textarea class="form-control" rows="10" id="skills" name="skills" placeholder="Skills..." maxlength="3000"><c:out value="${user.skills}"/></textarea></c:otherwise>
							      </c:choose>
							</div>
						 </div>
						
					</div>
					
					<div class="choosePrivate">
						<label for="checkbox">Choose which information will be private:</label>
						<div class="checkbox disabled">
						  <label><input type="checkbox" disabled>Full name</label>
						</div>
						<div class="checkbox">
							<label>
							<c:choose>
					    		<c:when test="${user.privateEmail==1}"><input type="checkbox" value="pr_email" name="pr_email" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_email" name="pr_email"></c:otherwise>
					    	</c:choose>
						    Email</label>
						</div>
						<div class="checkbox ">
						  <label>
						  	<c:choose>
					    		<c:when test="${user.privateTelephone==1}"> <input type="checkbox" value="pr_telephone" name="pr_telephone" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_telephone" name="pr_telephone"></c:otherwise>
					    	</c:choose>
						  Telephone</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateDateOfBirth==1}"><input type="checkbox" value="pr_dateOfBirth" name="pr_dateOfBirth" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_dateOfBirth" name="pr_dateOfBirth"></c:otherwise>
					     </c:choose>
						  Date of birth</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateGender==1}"> <input type="checkbox" value="pr_gender" name="pr_gender" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_gender" name="pr_gender"></c:otherwise>
					     </c:choose>
						  Gender</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateCountry==1}"><input type="checkbox" value="pr_country" name="pr_country" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_country" name="pr_country"></c:otherwise>
					     </c:choose>
						  Country of residence</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateCity==1}"> <input type="checkbox" value="pr_city" name="pr_city" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_city" name="pr_city"></c:otherwise>
					     </c:choose>
						  City of residence</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateWorkPos==1}"> <input type="checkbox" value="pr_workPos" name="pr_workPos" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_workPos" name="pr_workPos"></c:otherwise>
					     </c:choose>
						  Work position</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateInstitution==1}"> <input type="checkbox" value="pr_institution" name="pr_institution" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_institution" name="pr_institution"></c:otherwise>
					     </c:choose>
						  Employment institution</label>
						</div>
						<div class="checkbox">
						  <label>
						   <c:choose>
					    		<c:when test="${user.privateProfExp==1}"> <input type="checkbox" value="pr_profExp" name="pr_profExp" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_profExp" name="pr_profExp"></c:otherwise>
					     </c:choose>
						  Professional experience</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateEducation==1}"> <input type="checkbox" value="pr_education" name="pr_education" checked></c:when>
					    		<c:otherwise><input type="checkbox" value="pr_education" name="pr_education"></c:otherwise>
					     </c:choose>
						  Education</label>
						</div>
						<div class="checkbox">
						  <label>
						  <c:choose>
					    		<c:when test="${user.privateSkills==1}"> <input type="checkbox" value="pr_skills" name="pr_skills" checked></c:when>
					    		<c:otherwise><input type="checkbox"value="pr_skills" name="pr_skills"></c:otherwise>
					     </c:choose>
						  Skills</label>
						</div>
					</div>
					
					 <div class="form-group row buttons">
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
							 	<input type="reset" class="btn cancel" value="Go to Admin Page" onclick="window.location.href='${pageContext.request.contextPath}/ListUsers?action=getUsers';">
							</div>
							
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4"></div>
							
							<div class="col-xs-4 col-md-4 col-lg-4 col-sm-4">
							 	<button type="submit" class="btn btn-primary submit">Submit</button>
							</div>
								
					 </div> 
				  </form>
			</div>
		</div>			
		
		<c:import url="Footer.jsp" /> 
		
	</body>
</html>