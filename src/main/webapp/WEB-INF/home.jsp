<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Home</title>
		
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		
		
		<style type="text/css">
			body {
				font-family: Verdana, Geneva, sans-serif;
				padding: 0px;
				height: 100%;
				background-color:#D3EDF5;
			}
			#nav-header {
				margin-top:5%;
				background-color:#B8E6B8;
				height:40px;
				border:solid 1px;
			}
			#login-form {
			 	float:right;
			}
			#list {
				margin-left:30%;
			}

		</style>
	</head> 
	<body>
	<div id="nav-header">
		
		
		<button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">New User? Register here</button>

	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Modal Header</h4>
	      </div>
	      <div class="modal-body">
	        <p>Some text in the modal.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	
	  </div>
	</div>
	<div id="login-form">
		<form action="">
		  User:
		  <input type="text" name="User">
		 
		  Password:
		  <input type="text" name="Password">
		  
		  <input type="submit" value="Submit">
		  
		</form>
		</div>
	</div>
	
	<br><br>
	
	<div id="list">
	<h3>Listan över saker</h3>
	<br>
  	<ul>
  	 
  		<li>sak 1</li>
  		<li>sak 2</li>
  		<li>sak 3</li>
  		<li>sak 4</li>
  		<li>..</li>
  	
  	
  	</ul>
	</div>
	
	<hr>
	<p>Dom här knapparna ska bara visas när man är inloggad(?)</p>
	<button>Lägg till grej i listan knapp</button>(Redirektas till "lägga till saker i lista sidan")
	<br><br>
	<button>Betala </button>Visa/mastercard betalning för att höja sin saks rank i listan
	<hr>
	
	</body>
</html>