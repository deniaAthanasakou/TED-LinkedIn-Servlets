<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- custom -->
		<link rel="stylesheet" href="./css_files/main_css.css">
		<link rel="stylesheet" href="./css_files/welcome_page.css">
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<!-- jQuery library -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<!-- Latest compiled JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		
		<script src="./js_files/readURL.js"></script>
		<script src="./js_files/removeImage.js"></script>
		<title>Welcome Page</title>
		
	</head>
	<body>
		
	<div class="header">
		<div class="logoContainer">
			<a href="./WelcomePage.jsp" class="logo"><img src="images/logo.png" class="logo"/></a>
		</div>
	</div> 
	
		<div class="main">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-6 col-lg-offset-3">
						<h2>Sign in or Sign up</h2>
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#signin">Sign in</a></li>
							<li><a data-toggle="tab" href="#signup">Sign up</a></li>
						</ul>
			
						<div class="tab-content">
							<div id="signin" class="tab-pane fade in active">
								<h3>Sign in</h3>
								<form role="Form" method="POST" action="./LoginUser" accept-charset="UTF-8">
									<% if ( request.getAttribute( "loginError" ) != null ) { %>
										<p style="color:red"><%=request.getAttribute( "loginError" )%></p>
									<% } %>
									<div class="form-group">
										<input type="text" name="email" placeholder="Email..." class="form-control" required>
									</div>
									<div class="form-group">
										<input type="password" name="password" placeholder="Password..." class="form-control" required>
									</div>
									<div class="form-group" style="padding-top:20px;">
										<button type="submit" class="btn btn-primary">Submit</button>
									</div>
								</form>
							</div>
							<div id="signup" class="tab-pane fade">
								<h3>Sign Up</h3>
								<!--  -->
								<form role="Form" method="POST" action="./RegisterUser" accept-charset="UTF-8" enctype="multipart/form-data">
									<div class="form-group">
										<input type="text" name="email" placeholder="Email..." class="form-control" required>
									</div>
									<div class="form-group">
										<input type="password" name="password" placeholder="Password..." class="form-control" required>
									</div>
									<div class="form-group">
										<input type="password" name="password2" placeholder="Verify password..." class="form-control" required>
									</div>
									<div class="form-group">
										<input type="text" name="name" placeholder="Name..." class="form-control" required>
									</div>
									<div class="form-group">
										<input type="text" name="surname" placeholder="Surname..." class="form-control" required>
									</div>
									<div class="form-group">
										<input type="tel" name="telephone" placeholder="Telephone..." class="form-control">
									</div>
								    <div class="form-group">
								        <label> Upload Image</label>
								        <div class="input-group">
								            <span class="input-group-btn">
								                <span class="btn btn-default btn-file">
								                    Browse… <input type="file" id="imgInp" name ="imgInp" onchange="readURL(this);"/>
								                </span>
								            </span>
								            <input type="text" class="form-control" readonly>
								        </div>
								        <div id="uploadedImageDiv">
								         	<img id="uploadedImage" />
								         	<input type="button" value="Remove" onclick="javascript: removeImage();"/>
								        </div>
								    </div>
									<div class="form-group" style="padding-top:20px;">
										<button type="submit" class="btn btn-primary">Submit</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>	
			</div>
		</div>
		
		<jsp:include page="./jsp_files/Footer.jsp" />

	</body>
</html>