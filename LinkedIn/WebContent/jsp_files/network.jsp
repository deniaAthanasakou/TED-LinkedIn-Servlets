<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- custom -->
		<link rel="stylesheet" href="../css_files/main_css.css" type="text/css">
		<link rel="stylesheet" href="../css_files/user_network.css" type="text/css">
		<title>Network</title>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="container">
				<div class="searchContainer" >
			        
			        <br>
			            <div id="custom-search-input">
			                <div class="input-group col-md-12">
			                    <input type="text" class="form-control input-lg" placeholder="Search..." />
			                    <span class="input-group-btn">
			                        <button class="btn btn-info btn-lg" type="button">
			                            <i class="glyphicon glyphicon-search"></i>
			                        </button>
			                    </span>
			                </div>
			            </div>
				</div>
				
				
				<div class="myContainer">
					<%for(int i=0;i<4;i++){%>	<!-- 4 rows -->
						<div class="row">
							<%for(int j=0;j<3;j++){%>	<!-- 4 cols -->
								<a href="./UserNetworkInfo.jsp">
						    		<div class="col-lg-4 col-md-6 col-sm-12 col-xs-12" >
									    <table class="myTable">
									    	<tr>
										    	<td rowspan="3"><img  class="img-circle profileImage" src="../images/randomProfileImage.jpeg"></td>
										    	<td>Name Surname</td>
										    </tr>
										    <tr>
										    	<td>Short Description</td>
										    </tr>
										    <tr>
										    	<td><button type="button" class="btn btn-primary">Σύνδεση</button></td>
										    </tr>
									    </table>  
								    </div>
							    </a>
								
							<%}%>
						</div>
						
						
					<%}%>

				</div>
			</div>
		</div>
		
		
		<jsp:include page="Footer.jsp" /> 
	
	</body>
</html>