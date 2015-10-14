<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
	
  	<h1>Logga in</h1>
	<form action="/TopListan/Login">
	  User:<br>
	  <input type="text" name="User">
	  <br>
	  Password:<br>
	  <input type="text" name="Password">
	  <br><br>
	  <input type="submit" value="Submit">
	</form>

<br><br>
	<h1>Ny användare?</h1>
	<h2>Registrera dig här</h2>
	<form action="/TopListan/Register">
	  User:<br>
	  <input type="text" name="User">
	  <br>
	  Password:<br>
	  <input type="text" name="Password">
	  <br><br>
	  <input type="submit" value="Submit">
	</form>
	
	
	</body>
</html>