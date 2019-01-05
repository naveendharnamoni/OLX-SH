<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Olx Home</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>



<style>
.navbar-nav>li {
	float: right;
	padding-left: 10px;
	padding-right: 10px;
}
</style>

</head>
<body>

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">

		<a class="navbar-brand" href="#"> <img src="${pageContext.request.contextPath}/resources/olx.jpg" alt="Logo"
			style="width: 40px;">
		</a>

		<!-- Toggler/collapsibe Button -->
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>

		<!-- Navbar links -->
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav">
				<!-- Dropdown -->
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbardrop"
					data-toggle="dropdown"> Select City </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="#">Hyderabad</a> <a
							class="dropdown-item" href="#">Secunderabad</a> <a
							class="dropdown-item" href="#">Bhimavaram</a>
					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbardrop"
					data-toggle="dropdown"> Select Locality </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="#">Gachibowli</a> <a
							class="dropdown-item" href="#">Madhapur</a> <a
							class="dropdown-item" href="#">Kukatpally</a>
					</div></li>


			</ul>

			<ul class="navbar-nav ml-auto">
				<li class="nav-item"><a class="nav-link" data-toggle="modal"
					data-target="#signIn">Sign In</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="modal"
					data-target="#signUp">Sign Up</a></li>

			</ul>

		</div>


	</nav>
	<br>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<form class="navbar-form navbar-left" role="search">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Search for awesome stuff"> <span
							class="input-group-btn"><button type="submit"
								class="btn btn-default">Search</button></span>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="container">
		<!-- Nav tabs -->
		<ul class="nav nav-tabs" role="tablist">
			<li class="nav-item"><a class="nav-link active"
				data-toggle="tab" href="#home">Home</a></li>
			<c:forEach items="${categoriesWithSubCategories}" var="category">
				<li class="nav-item"><a class="nav-link" data-toggle="tab"
					href="#${category.id}"><img
						src="${pageContext.request.contextPath}/resources/categories/${category.iconPath}" style="width: 40px;"></a>
				</li>
			</c:forEach>

		</ul>

		<!-- Tab panes -->
		<div class="tab-content">


			<div id="home" class="container tab-pane active">
				<br>

				<c:set var="i" value="0"></c:set>

				<c:forEach items="${items_posted_date_desc}" var="item">

					<c:if test="${i%3 eq 0}">
						<div class="row">
					</c:if>
					<div class="col-sm-4">
					<c:if test="${ mark_item_sold ne true}">
					<li class="nav-item">
					<a class="badge badge-success" data-toggle="modal"
					data-target="#signIn">Grab It</a></li>
					
					
					</c:if>


						<div id="${item.id}" class="carousel slide" data-ride="carousel">

							<!-- Indicators -->
							<ul class="carousel-indicators">
								<li data-target="#${item.id}" data-slide-to="0" class="active"></li>
								<li data-target="#${item.id}" data-slide-to="1"></li>
								<li data-target="#${item.id}" data-slide-to="2"></li>
							</ul>

							<!-- The slideshow -->
							<div class="carousel-inner">
								<div class="carousel-item active">
									<img src="${pageContext.request.contextPath}/resources/items/${item.imagesList[0]}" width="100%"
										height="100%" alt="image">
								</div>

								<c:forEach begin="1" end="${ fn:length(item.imagesList) - 1}" var="j">
									<div class="carousel-item">
										<img src="${pageContext.request.contextPath}/resources/items/${item.imagesList[j]}" alt="image" width="100%" height="100%">
									</div>
								</c:forEach>

							</div>

							<!-- Left and right controls -->
							<a class="carousel-control-prev" href="#${item.id}"
								data-slide="prev"> <span class="carousel-control-prev-icon"></span>
							</a> <a class="carousel-control-next" href="#${item.id}"
								data-slide="next"> <span class="carousel-control-next-icon"></span>
							</a>
						</div>

						<p>${item.description}</p>
					</div>


					<c:if test="${i%3 eq 2}">
			</div>
			</c:if>
			<c:set var="i" value="${ i + 1}"></c:set>
			</c:forEach>
			<c:if test="${ i % 3 ne 0}">
		</div>
		</c:if>
	<c:remove var="i"/>
	</div>


	<c:forEach items="${categoriesWithSubCategories}" var="category">

		<div id="${category.id}" class="container tab-pane fade">

			<c:set var="i" value="0"></c:set>					
			<c:forEach items="${category.subCategoriesList}" var="subCategory">

				<c:if test="${i%3 eq 0}">
					<div class="row">					
				</c:if>
				<div class="col-sm-4">
	
					<img alt="car" src="${pageContext.request.contextPath}/resources/subcategories/${subCategory.iconPath}" width="70%"
						height="70%">
				</div>		
				<c:if test="${i%3 eq 2}">
		      		</div>		   
		   		</c:if>
		   		<c:set var="i" value="${ i + 1}"></c:set>
			</c:forEach>
			<c:if test="${ i % 3 ne 0}">
		 		</div>
			</c:if>	
				
		</div>
	</c:forEach>
		 
		<c:remove var="i"/>
</div>
	<!-- The Modal -->
	<div class="modal fade" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Modal Heading</h4>
					<button type="button" class="close" data-dismiss="modal">×</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">Modal body..</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
	<!-- The Modal -->
	<div class="modal fade" id="signIn">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Sign In</h4>
					<button type="button" class="close" data-dismiss="modal">×</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action="signIn" method="post">
						<div class="form-group">
							<input type="hidden" class="form-control" id="action"
								name="action" value="login">
						</div>
						<div class="form-group">
							<label for="user_email">Email address</label> <input
								type="email" class="form-control" id="user_email"
								name="user_email">
						</div>
						<div class="form-group">
							<label for="user_password">Password</label> <input
								type="password" class="form-control" id="user_password"
								name="user_password">
						</div>

						<div class="form-check-inline">
							<label class="form-check-label"> <input type="radio"
								class="form-check-input" name="loginAs" value="user">
								User
							</label>
						</div>
						<div class="form-check-inline">
							<label class="form-check-label"> <input type="radio"
								class="form-check-input" name="loginAs" value="moderator">Moderator
							</label>
						</div>
						<br>
						<button type="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>



			</div>
		</div>
	</div>


	<!-- The Modal -->
	<div class="modal fade" id="signUp">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Register Here</h4>
					<button type="button" class="close" data-dismiss="modal">×</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action="signUp" method="post" >
						<div class="form-group">
							<%-- <form:hidden class="form-control" id="action"
								path="action" value="user_registration"/> --%>
						</div>
						<div class="form-group">
							<label for="first_name">First Name</label> <input type="text"
								class="form-control" id="firstName" name="firstName" />
						</div>
						<div class="form-group">
							<label for="last_name">Last Name</label> <input type="text"
								class="form-control" id="lastName" name="lastName" />
						</div>
						<div class="form-group">
							<label for="email">Email</label> <input type="email"
								class="form-control" id="email" name="email" />
						</div>

						<div class="form-group">
							<label for="password">Password</label> <input type="password"
								class="form-control" id="password" name="password" />
						</div>
						<div class="form-group">
							<label for="phone_number">Phone Number</label> <input
								type="number" class="form-control" id="phoneNumber" name="phoneNumber" />
						</div>
						<div class="form-group">
							<label for="dob">Date of Birth</label> <input type="date"
								class="form-control" id="dateOfBirth" name="dateOfBirth"  />
						</div>

						<br>
						<button type="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>


			</div>
		</div>
	</div>


</body>
</html>