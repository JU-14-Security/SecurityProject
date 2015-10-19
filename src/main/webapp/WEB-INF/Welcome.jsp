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
   #header {
    height: 80px;
    margin: 0px auto;
    background-color: #FFFFFF;
    color: #2879b3;
    padding-left: 40%;
    padding-top: 1%;
   }
   .stencil {
    margin: 0px auto;
    font-size: 4em;
    font-family: Impact, Charcoal, sans-serif;
    display: inline;
    }
   #login-form {
     float:right;
   }
   #list {
    margin-left:30%;
   }
   #Login-Box {
    background-color:#FFFFFF;
    margin-left:35%;
    margin-right:35%;
    margin-top:5%;
    padding:15px;
    border-style: solid;
    border-width: 1px;
    border-color: black;
   }

  </style>
 </head> 
 <body>
  
 <div id="header">
  <h1 class=stencil>TopListan  </h1>
 </div>
 
<div id="Login-Box">
 <h1>Log in</h1>
 <p style="Color:red;">${errorLog}</p>
 <form action="/TopListan/Login" method="post">
  Username:<input type="text" id="username" name="username" placeholder="Enter Username" required>
  <br>
  Password: <input type="password" id="password" name="password" required>
  <br>
  <input type="submit" class="btn-primary btn-default" value="Log in">
 </form>

 
 <br>
 <hr>

 <h1>New User? Register here</h1>
<p style="Color:red;">${errorReg}<p> 
 <form action="/TopListan/Register" method="post">
  Username:<input type="text" id="username" name="username" placeholder="Enter Username" required>
  <br>
  Password: <input type="password" id="password" name="password" required>
  <br>
  <input type="submit" class="btn-primary btn-default" value="Register">
 </form>  

</div>
 </body>
</html>