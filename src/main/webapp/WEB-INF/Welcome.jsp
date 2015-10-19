<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
		
	
<div>
	<h1>Log in</h1>
	<p style="Color:red;">${error}</p>
	<form action="/TopListan/Login" method="post">
		Username:<input type="text" id="username" name="username" placeholder="Enter Username" required>
		<br>
		Password: <input type="password" id="password" name="password" required>
		<br>
		<input type="submit" class="btn-primary btn-default" value="Log in">
	</form>
</div>
	
	<br>
<div>
<h3 style"Color:red;">${error}</h3>	
	<h1>New User? Register here</h1>
	<form action="/TopListan/Register" method="post">
		Username:<input type="text" id="username" name="username" placeholder="Enter Username" required>
		<br>
		Password: <input type="password" id="password" name="password" required>
		<br>
		<input type="submit" class="btn-primary btn-default" value="Log in">
	</form>		
</div>

	</body>
</html>