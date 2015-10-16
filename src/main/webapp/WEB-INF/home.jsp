<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
			
		<!--        REGISTRERING                       -->	
			<button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">New User? Register here</button>
	
		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
		  <div class="modal-dialog">
		
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">Register</h4>
		      </div>
		      <div class="modal-body">
		        <form method="POST" action="Register">
					   <table border="0">
		                <tr>
		                    <td>Username:</td>
		                    <td><input type="text" name="Username"></td>
		                </tr>
		                <tr>
		                    <td>Password:</td>
		                    <td><input type="password" name="Password"></td>
		                </tr>
		                <tr>
		                    <td colspan="2" align="center"><input type="submit" value="Register" /></td>
		                </tr>
	            </table>
			</form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		
		  </div>
	</div>
		
		<!--        LOGIN FORM             -->
		<div id="login-form">
			<form action="Login" method="POST">
			  User:
			  <input type="text" name="Username">
			 
			  Password:
			  <input type="password" name="Password">
			  
			  <input type="submit" value="Log in">
			  
			</form>
			</div>
</div>
	
	<br>
	
		
	<ul>
	<c:forEach items="${TopList}" var="item">
			
		<li>${item.product}    ${item.productUrl}</li>
				
			
	</c:forEach> 
	</ul>
	<br>
	
		
	<hr>
	<!--            ADD ITEM                -->
	<button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal2">Add item to list</button>

	<!-- Modal -->
	<div id="myModal2" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Add Product</h4>
	      </div>
	      <div class="modal-body">
	        <form method="POST" action="addItem">
				   <table border="0">
	                <tr>
	                    <td>Product name:</td>
	                    <td><input type="text" name="Product"></td>
	                </tr>
	                <tr>
	                    <td>Product Url:</td>
	                    <td><input type="text" name="Producturl"></td>
	                </tr>
	                <tr>
	                    <td colspan="2" align="center"><input type="submit" value="add item" /></td>
	                </tr>
            </table>
		</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	
	  </div>
	</div>
		
	
	<br><br>
	<button>Betala </button>Visa/mastercard betalning för att höja sin saks rank i listan
	<hr>
	
	</body>
</html>