<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>

<meta charset="utf-8" >

<title>Home</title>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">
	var regUsername=false;
	var regPassword=false;
	var logUsername=false;
	var logPassword=false;	
	
	function validateRegUsernameInput(input) {

		if (!input.value.match(/^[0-9A-Ö]+$/)) {

			input.style.backgroundColor = "#F78181";
			
			regUsername=false;
		} else {
			input.style.backgroundColor = "#9AFF9A";
			regUsername=true;
		}
		if(input.value=="" ||input.value==null){
			input.style.backgroundColor = "#FFFFFF";
		}

	}
	function validateRegPasswordInput(input){
		if(input.value!=""){
				input.style.backgroundColor = "#9AFF9A";
				regPassword=true;		

			}else{
				
				input.style.backgroundColor = "#F78181";

				regPassword=false;
			}
		
		if(input.value=="" ||input.value==null){
			input.style.backgroundColor = "#FFFFFF";
		}
			
		
	}
	function okRegForm(){
		if(regUsername==true && regPassword==true){
			document.getElementById("regbtn").disabled = false;
			document.getElementById("regbtn").style.opacity = 1;
			
			
		}else{
			document.getElementById("regbtn").disabled = true;
			document.getElementById("regbtn").style.opacity = 0.5;					
		}
		
	}
	function validateLogUsernameInput(input) {

		if (!input.value.match(/^[0-9A-Ö]+$/)) {

			input.style.backgroundColor = "#F78181";
			
			logUsername=false;
		} else {
			input.style.backgroundColor = "#9AFF9A";
			logUsername=true;
		}
		if(input.value=="" ||input.value==null){
			input.style.backgroundColor = "#FFFFFF";
		}

	}
	function validateLogPasswordInput(input){
		if(input.value!=""){
				input.style.backgroundColor = "#9AFF9A";
				logPassword=true;		

			}else{
				
				input.style.backgroundColor = "#F78181";

				logPassword=false;
			}
		
		if(input.value=="" ||input.value==null){
			input.style.backgroundColor = "#FFFFFF";
		}
			
		
	}
	function okLogForm(){
		if(logUsername==true && logPassword==true){
			document.getElementById("logbtn").disabled = false;
			document.getElementById("logbtn").style.opacity = 1;
			
			
		}else{
			document.getElementById("logbtn").disabled = true;
			document.getElementById("logbtn").style.opacity = 0.5;					
		}
		
	}

	function disablebtn(button){
		button.disabled = true;
		
	}
</script>

<style type="text/css">
body{
    background: -webkit-linear-gradient(Left, #000000,#1d1d1d,#1d1d1d,#1d1d1d,#1d1d1d,#1d1d1d,#000000);
   
   }

   #header {
    height: 80px;
    margin: 0px auto;
    background-color: #1d1d1d;
    color: #FFF;
    padding-left: 45%;
    padding-top: 1%;
    background: -webkit-linear-gradient(Left, #000000,#1d1d1d,#1d1d1d,#1d1d1d,#1d1d1d,#1d1d1d,#000000);
    text-shadow: 5px 5px #000000;
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
#regbtn {
	opacity: 0.5;
}

#logbtn {
	opacity: 0.5;
}
</style>
</head>
<body>

	<div id="header">
		<h1 class=stencil>TopListan</h1>
	</div>

	<div id="Login-Box">
		<h1>Log in</h1>
		<p style="Color: red;">${errorLog}</p>
		<form action="/TopListan/Login" method="post">
			Username:<input type="text" id="loginusername" name="username"
				placeholder="Enter Username" required oninput="validateLogUsernameInput(this), okLogForm()"> 
				<br> Password:
			<input type="password" id="loginpassword" name="password" required 
			oninput="validateLogPasswordInput(this), okLogForm()" AUTOCOMPLETE='OFF'>
			<br> <input id="logbtn" type="submit"
				class="btn-primary btn-default" value="Log in" disabled>
		</form>


		<br>
		<hr>

		<h1>New User? Register here</h1>
		<p style="Color: red;">${errorReg}
		<p>
		<form action="/TopListan/Register" method="post">
			Username:<input type="text" id="regusername" name="username"
				placeholder="Enter Username" required oninput="validateRegUsernameInput(this), okRegForm()">
			<br> 
			Password: <input type="password" id="regpassword"
				name="password" required oninput="validateRegPasswordInput(this), okRegForm()" AUTOCOMPLETE='OFF'> <br> 
				<input id="regbtn"
				type="submit" class="btn-primary btn-default" value="Register" disabled>
		</form>
	</div>

</body>
</html>