<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/createJob.css" type="text/css">
		<title>Edit job</title>
		
		<script src="${pageContext.request.contextPath}/js_files/setSelected.js"></script>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="createJobInfo">
				<form method="post" action="${pageContext.request.contextPath}/JobHandle?action=editJob&jobId=${job.id.jobId}" accept-charset="utf-8">
					<div class="infoLabel">
						<h3>Main information</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
								    <label class="sr-only" for="jobTitle">Job title</label>
								    <input type="text" class="form-control" id="jobTitle" name="jobTitle" placeholder="Job title" value="${job.title}" maxlength="60" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
								    <label class="sr-only" for="jobIndustry">Company</label>
								    <input type="text" class="form-control" id="jobIndustry" name="jobIndustry" placeholder="Company" value="${job.company}" maxlength="45" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
								    <label class="sr-only" for="jobLocation">Location</label>
								    <input type="text" class="form-control" id="jobLocation" name="jobLocation" placeholder="Location" maxlength="200" value="${job.location}" required>
								</div>
							</div>
						</div>
					</div>
					
					<div class="infoLabel">
						<h3>Add more details</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<c:if test="${requestScope.jobFunctionError != null }">
										<div class="alert alert-danger">
  											<strong>Error!</strong> ${requestScope.jobFunctionError}
										</div>
									</c:if>
								    <label for="jobFunction">Job function (Select up to 3)*</label>
								    <select multiple class="form-control" id="jobFunction" name="jobFunction" required>
								    	<option value="">Choose one...</option>
									    <option value="Accounting">Accounting</option>
									    <option value="Administrative">Administrative</option>
									    <option value="Arts and Design">Arts and Design</option>
									    <option value="Business Development">Business Development</option>
									    <option value="Community & Social Services">Community &amp; Social Services</option>
									    <option value="Consulting">Consulting</option>
									    <option value="Education">Education</option>
									    <option value="Engineering">Engineering</option>
									    <option value="Entrepreneurship">Entrepreneurship</option>
									    <option value="Finance">Finance</option>
									    <option value="Healthcare Services">Healthcare Services</option>
									    <option value="Human Resources">Human Resources</option>
									    <option value="Information Technology">Information Technology</option>
									    <option value="Legal">Legal</option>
									    <option value="Marketing">Marketing</option>
									    <option value="Media & Communications">Media &amp; Communications</option>
									    <option value="Military & Protective Services">Military &amp; Protective Services</option>
									    <option value="Operations">Operations</option>
									    <option value="Product Management">Product Management</option>
									    <option value="Program & Product Management">Program &amp; Product Management</option>
									    <option value="Purchasing">Purchasing</option>
									    <option value="Quality Assurance">Quality Assurance</option>
									    <option value="Real Estate">Real Estate</option>
									    <option value="Rersearch">Rersearch</option>
									    <option value="Sales">Sales</option>
									    <option value="Support">Support</option>
							    	</select>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
								    <label for="employmentType">Employment type*</label>
								    <select class="form-control" id="employmentType" name="employmentType" required>
								    	<option value="">Choose one...</option>
									    <option value="Full-time">Full-time</option>
									    <option value="Part-time">Part-time</option>
									    <option value="Contract">Contract</option>
									    <option value="Temporary">Temporary</option>
									    <option value="Volunteer">Volunteer</option>
									    <option value="Internship">Internship</option>
							    	</select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<c:if test="${requestScope.companyTypesError != null }">
										<div class="alert alert-danger">
  											<strong>Error!</strong> ${requestScope.companyTypesError}
										</div>
									</c:if>
								    <label for="companyIndustry">Company industry (Select up to 3)*</label>
								    <select multiple class="form-control" id="companyIndustry" name="companyIndustry" required>
								    	<option value="">Choose one...</option>
								    	<option value="Accounting">Accounting</option>
										<option value="Airlines/Aviation">Airlines/Aviation</option>
										<option value="Alternative Dispute Resolution">Alternative Dispute Resolution</option>
										<option value="Alternative Medicine">Alternative Medicine</option>
										<option value="Animation">Animation</option>
										<option value="Apparel & Fashion">Apparel &amp; Fashion</option>
										<option value="Architecture & Planning">Architecture &amp; Planning</option>
										<option value="Arts and Crafts">Arts and Crafts</option>
										<option value="Automotive">Automotive</option>
										<option value="Aviation & Aerospace">Aviation &amp; Aerospace</option>
										<option value="Banking">Banking</option>
										<option value="Biotechnology">Biotechnology</option>
										<option value="Building Materials">Broadcast Media</option>
										<option value="Building Materials">Building Materials</option>
										<option value="Business Supplies and Equipment">Business Supplies and Equipment</option>
										<option value="Capital Markets">Capital Markets</option>
										<option value="Chemicals">Chemicals</option>
										<option value="Civic & Social Organization">Civic &amp; Social Organization</option>
										<option value="Civil Engineering">Civil Engineering</option>
										<option value="Commercial Real Estate">Commercial Real Estate</option>
										<option value="Computer & Network Security">Computer &amp; Network Security</option>
										<option value="Computer Games">Computer Games</option>
										<option value="Computer Networking">Computer Networking</option>
										<option value="Computer Software">Computer Software</option>
										<option value="Computer Hardware">Computer Hardware</option>
										<option value="Construction">Construction</option>
										<option value="Consumer Electronics">Consumer Electronics</option>
										<option value="Consumer Goods">Consumer Goods</option>
										<option value="Consumer Services">Consumer Services</option>
										<option value="Cosmetics">Cosmetics</option>
										<option value="Dairy">Dairy</option>
										<option value="Defense & Space">Defense &amp; Space</option>
										<option value="Design">Design</option>
										<option value="Education Management">Education Management</option>
										<option value="E-Learning">E-Learning</option>
										<option value="Electrical/Electronic Manufacturing">Electrical/Electronic Manufacturing</option>
										<option value="Entertainment">Entertainment</option>
										<option value="Environmental Services">Environmental Services</option>
										<option value="Events Services">Events Services</option>
										<option value="Executive Office">Executive Office</option>
										<option value="Facilities Services">Facilities Services</option>
										<option value="Farming">Farming</option>
										<option value="Financial Services">Financial Services</option>
										<option value="Fine Art">Fine Art</option>
										<option value="Fishery">Fishery</option>
										<option value="Food & Beverages">Food &amp; Beverages</option>
										<option value="Food Production">Food Production</option>
										<option value="Fund-Raising">Fund-Raising</option>
										<option value="Furniture">Furniture</option>
										<option value="Gambling & Casinos">Gambling &amp; Casinos</option>
										<option value="Glass, Ceramics & Concrete">Glass, Ceramics &amp; Concrete</option>
										<option value="Government Administration">Government Administration</option>
										<option value="Government Relations">Government Relations</option>
										<option value="Graphic Design">Graphic Design</option>
										<option value="Health, Wellness and Fitness">Health, Wellness and Fitness</option>
										<option value="Higher Education">Higher Education</option>
										<option value="Hospital & Health Care">Hospital &amp; Health Care</option>
										<option value="Hospitality">Hospitality</option>
										<option value="Human Resources">Human Resources</option>
										<option value="Import and Export">Import and Export</option>
										<option value="Individual & Family Services">Individual &amp; Family Services</option>
										<option value="Industrial Automation">Industrial Automation</option>
										<option value="Information Services">Information Services</option>
										<option value="Information Technology and Services">Information Technology and Services</option>
										<option value="Insurance">Insurance</option>
										<option value="International Affairs">International Affairs</option>
										<option value="International Trade and Development">International Trade and Development</option>
										<option value="Internet">Internet</option>
										<option value="Investment Banking">Investment Banking</option>
										<option value="Investment Management">Investment Management</option>
										<option value="Judiciary">Judiciary</option>
										<option value="Law Enforcement">Law Enforcement</option>
										<option value="Law Practice">Law Practice</option>
										<option value="Legal Services">Legal Services</option>
										<option value="Legislative Office">Legislative Office</option>
										<option value="Leisure, Travel & Tourism">Leisure, Travel &amp; Tourism</option>
										<option value="Libraries">Libraries</option>
										<option value="Logistics and Supply Chain">Logistics and Supply Chain</option>
										<option value="Luxury Goods & Jewelry">Luxury Goods &amp; Jewelry</option>
										<option value="Machinery">Machinery</option>
										<option value="Management Consulting">Management Consulting</option>
										<option value="Maritime">Maritime</option>
										<option value="Market Research">Market Research</option>
										<option value="Marketing and Advertising">Marketing and Advertising</option>
										<option value="Mechanical or Industrial Engineering">Mechanical or Industrial Engineering</option>
										<option value="Media Production">Media Production</option>
										<option value="Medical Devices">Medical Devices</option>
										<option value="Medical Practice">Medical Practice</option>
										<option value="Mental Health Care">Mental Health Care</option>
										<option value="Military">Military</option>
										<option value="Mining & Metals">Mining &amp; Metals</option>
										<option value="Motion Pictures and Film">Motion Pictures and Film</option>
										<option value="Museums and Institutions">Museums and Institutions</option>
										<option value="Music">Music</option>
										<option value="Nanotechnology">Nanotechnology</option>
										<option value="Newspapers">Newspapers</option>
										<option value="Non-Profit Organization Management">Non-Profit Organization Management</option>
										<option value="Oil & Energy">Oil &amp; Energy</option>
										<option value="Online Media">Online Media</option>
										<option value="Outsourcing/Offshoring">Outsourcing/Offshoring</option>
										<option value="Package/Freight Delivery">Package/Freight Delivery</option>
										<option value="Packaging and Containers">Packaging and Containers</option>
										<option value="Paper & Forest Products">Paper &amp; Forest Products</option>
										<option value="Performing Arts">Performing Arts</option>
										<option value="Pharmaceuticals">Pharmaceuticals</option>
										<option value="Philanthropy">Philanthropy</option>
										<option value="Photography">Photography</option>
										<option value="Plastics">Plastics</option>
										<option value="Political Organization">Political Organization</option>
										<option value="Primary/Secondary Education">Primary/Secondary Education</option>
										<option value="Printing">Printing</option>
										<option value="Professional Training & Coaching">Professional Training &amp; Coaching</option>
										<option value="Program Development">Program Development</option>
										<option value="Public Policy">Public Policy</option>
										<option value="Public Relations and Communications">Public Relations and Communications</option>
										<option value="Public Safety">Public Safety</option>
										<option value="Publishing">Publishing</option>
										<option value="Railroad Manufacture">Railroad Manufacture</option>
										<option value="Ranching">Ranching</option>
										<option value="Real Estate">Real Estate</option>
										<option value="Recreational Facilities and Services">Recreational Facilities and Services</option>
										<option value="Religious Institutions">Religious Institutions</option>
										<option value="Renewables & Environment">Renewables &amp; Environment</option>
										<option value="Research">Research</option>
										<option value="Restaurants">Restaurants</option>
										<option value="Retail">Retail</option>
										<option value="Security and Investigations">Security and Investigations</option>
										<option value="Semiconductors">Semiconductors</option>
										<option value="Shipbuilding">Shipbuilding</option>
										<option value="Sporting Goods">Sporting Goods</option>
										<option value="Sports">Sports</option>
										<option value="Staffing and Recruiting">Staffing and Recruiting</option>
										<option value="Supermarkets">Supermarkets</option>
										<option value="Telecommunications">Telecommunications</option>
										<option value="Textiles">Textiles</option>
										<option value="Think Tanks">Think Tanks</option>
										<option value="Tobacco">Tobacco</option>
										<option value="Translation and Localization">Translation and Localization</option>
										<option value="Transportation/Trucking/Railroad">Transportation/Trucking/Railroad</option>
										<option value="Utilities">Utilities</option>
										<option value="Venture Capital & Private Equity">Venture Capital &amp; Private Equity</option>
										<option value="Veterinary">Veterinary</option>
										<option value="Warehousing">Warehousing</option>
										<option value="Wholesale">Wholesale</option>
										<option value="Wine and Spirits">Wine and Spirits</option>
										<option value="Wireless">Wireless</option>
										<option value="Writing and Editing">Writing and Editing</option>
							    	</select>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
								    <label for="seniorityLevel">Seniority level*</label>
								    <select class="form-control" id="seniorityLevel" name="seniorityLevel" required>
								    	<option value="">Choose one...</option>
									    <option value="Internship">Internship</option>
									    <option value="Entry level">Entry level</option>
									    <option value="Associate">Associate</option>
									    <option value="Mid-Senior level">Mid-Senior level</option>
									    <option value="Director">Director</option>
									    <option value="Executive">Executive</option>
							    	</select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="jobDescription">Job description*</label>
									<textarea class="form-control" rows="3" name="jobDescription" id="jobDescription" maxlength="10000" required>${job.description}</textarea>
								</div>
							</div>
						</div>
					</div>
					
					<div class="infoLabel">
						<h3>Skills &amp; Experience</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<c:if test="${requestScope.skillsError != null }">
										<div class="alert alert-danger">
  											<strong>Error!</strong> ${requestScope.skillsError}
										</div>
									</c:if>
								    <label for="skills">What are some of the skills needed for this job? (Select up to 10)*</label>
								    <textarea class="form-control" rows="2" name="skills" id="skills" placeholder="Skills..." maxlength="1000" required>${job.skills}</textarea>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<c:if test="${requestScope.experienceFromToError != null }">
									<div class="alert alert-danger">
										<strong>Error!</strong> ${requestScope.experienceFromToError}
									</div>
								</c:if>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">	
									<label for="fromYears">What range of relevant experience are you looking for? From*</label>
									<input type="number" class="form-control" id="fromYears" name="fromYears" value="${job.experienceFrom}" min="0" max="50" required>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">	
									<label for="toYears">To*</label>
									<input type="number" class="form-control" id="toYears" name="toYears" value="${job.experienceTo}" min="0" max="50" required>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<c:if test="${requestScope.educationLevelError != null }">
										<div class="alert alert-danger">
  											<strong>Error!</strong> ${requestScope.educationLevelError}
										</div>
									</c:if>
								    <label for="educationLevel">What level of education are you looking for? (Select up to 5)*</label>
								    <select multiple class="form-control" id="educationLevel" name="educationLevel" required>
								    	<option value="">Choose one...</option>
									    <option value="High School Diploma">High School Diploma</option>
									    <option value="Associate's Degree">Associate's Degree</option>
									    <option value="Bachelor's Degree">Bachelor's Degree</option>
									    <option value="Master's Degree">Master's Degree</option>
									    <option value="Master of Business Administration">Master of Business Administration</option>
									    <option value="Doctor of Philosophy">Doctor of Philosophy</option>
									    <option value="Doctor of Medicine">Doctor of Medicine</option>
									    <option value="Doctor of Law">Doctor of Law</option>
							    	</select>
								</div>
							</div>
						</div>
					</div>
					
					<div class="infoLabel">
						<h3>Set your budget</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="dailyMoney">Daily budget in EUR*</label>
									<input type="number" step=0.01 class="form-control" id="dailyMoney" name="dailyMoney" value="${job.dailySalary}" min="0" required>
								</div>
							</div>
						</div>
					</div>
					
					<div class="pull-left" style="margin-left:10px;">
						<button type="button" class="btn btn-danger" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/jobs.jsp'"><i class="glyphicon glyphicon-remove"></i> Cancel</button>
						<button type="reset" class="btn btn-warning"><i class="glyphicon glyphicon-trash"></i> Clear</button>
					</div>
					<button type="submit" class="btn btn-primary" style="float: right; margin-right:10px;"><i class="glyphicon glyphicon-ok"></i> Submit edit</button>
				</form>
			</div>
		</div>
		
		<jsp:include page="Footer.jsp"/>
		
		<script type='text/javascript'>
			setSelectedEducationLevel("${job.educationLevel}");
			setSelectedJobFunctions("${job.jobFunction}");
			setSelectedJobCompanyTypes("${job.jobCompanyType}");
			setSelectedJobType("${job.jobType}");
			setSelectedExperience("${job.experience}");
		</script>
	</body>
</html>