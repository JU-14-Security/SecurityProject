<!DOCTYPE html>

<%@ page language="java" contentType="text/html;  charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<meta charset="utf-8">
<title>Home</title>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script charset="UTF-8"
		src="https://ssl.ditonlinebetalingssystem.dk/integration/ewindow/paymentwindow.js"
		type="text/javascript"></script>	

<script type="text/javascript">

var amount=0;
var selectedID;
$('body').on('hidden.bs.modal', '.modal', function () {
	
    $(this).removeData('bs.modal');
  });

function hideListPick()
{
    $("#pickItem").hide();
}
function hidePayment()
{
    $("#payment-div").hide();
    document.getElementById("payment-amount").value=0;
    amount=0;
    if(typeof paymentwindow !== 'undefined'){
    paymentwindow.close();
    }
}
function showListPick()
{
    $("#pickItem").show();
 	
}

function checkAmountInput(input){
	var inputarray=[];
	inputarray=input.value;
	 
	if(input.value!="" && input.value>0 && inputarray[0]!=0){
	
		document.getElementById("payment-next").disabled=false;
		document.getElementById("payment-next").style.opacity=1;
		
	}else{
		document.getElementById("payment-next").disabled=true;
		document.getElementById("payment-next").style.opacity=0.5;
		
	}
	
}

function showPayment()
{
    $("#payment-div").show();
    amount=document.getElementById("payment-amount").value;

    amount=amount*100;
  
	paymentwindow = new PaymentWindow({
		'merchantnumber' : "8021018",
		'amount' : amount,
		'currency' : "SEK",
		'windowstate' : "4",
		'language': "2"
	});
	
	paymentwindow.on('completed', function(params){
	console.log("prutt");
		$.ajax({
	        type: 'POST',
	        url: '/TopListan/Update',
	        data: { id: selectedID,
	        		amount: amount},
	        cache: false,
	        success: function () {
	        	window.location.reload();
	        }
	    });
		
		
		
	});
	paymentwindow.append('payment-div');
	paymentwindow.open();

}

function changeFunc() {
    var selectBox = document.getElementById("selectBox");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    console.log(selectedValue);
    selectedID=selectedValue;
   }
</script>
<style type="text/css">

#wrapper { width:800px; margin:40px auto; }

ol,li,h1,h3,form,body,html,p{margin:0; padding:0;}

h3 { 
	font-style:italic; 
	border-left:10px solid #eee; 
	padding:10px 20px; 
	margin:30px 0; 
	color:#eee; 
	display: table; margin: 0 auto; 
}

.textInput { 
	border: 1px solid #FFFFFF; 
	background: #1d1d1d; 
	color: #ffffff; 
	font-size: 1.2em;
 } 
body { 
	
background: -webkit-linear-gradient(top, #000000, #1d1d1d, #1d1d1d,#1d1d1d,#1d1d1d,#1d1d1d,#1d1d1d);
	
}

#list2 { width:114%; }
#list2 ol { font-style:italic; font-family:Georgia, Times, serif; font-size:24px; color:#bfe1f1; display: table; margin: auto; }
#list2 ol li { }
#list2 ol li p { padding:8px; font-style:normal; font-family:Arial; font-size:15px; color:#eee; border-left: 1px solid #999; }
#list2 ol li p em { display:block; margin: 0 auto; }

#login-form {
	float: right;
}
	

#header {
	height: 80px;
	margin: 0px auto;
	background-color: 1d1d1d;
	color: #fff;
	padding-left: 40%;
	padding-top: 1%;

}
#menu-header {
	border-style: solid;
	border-width: 1px;
	border-color: white;
	height: 40px;
	margin: 0px auto;
	background-color: 1d1d1d;
	color: #FFF;
	padding-left: 25%;
	padding-top: 1%;

}


.stencil {
	margin: 0px auto;
	font-size: 4em;
	font-family: Impact, Charcoal, sans-serif;
	display: inline;
}

#menu-items {
	margin: 0px auto;
}
.scrollable-menu {
	height: auto;
	max-height: 200px;
	overflow-x: hidden;
}
#pickItem.li:hover {
	background-color: #A0B7FF;
}
.button {
	background: #000000;
	color: ffffff;
	border: 1px solid white;
	padding: -2px;
	padding-left: -10px;
	font-weight: bold;
	font-size: 120%;
	text-transform: uppercase;

}
.button-shown {

background: #1d1d1d;
	color: #ffffff;
	border: 2px solid white;
	border-color: #FFF;
	font-weight: bold;
	font-size: 120%;
	text-transform: uppercase;

}

.modal-custom {
	color: #FFF;
	background: #1d1d1d;
	background-image: url("../images/bg.png");
	background-repeat: repeat-x;
}
</style>
</head>
<body>
<div id="wrapper">
	<div id="header">
		<h1 class=stencil>TopListan</h1>
	</div>
	<div id="menu-header">
		<div id="menu-items">
						<form style="float: right; margin-right: 20%;"
				action="/TopListan/Logout">
				<input class="button" type="submit" value="Log out" />
			</form>
			<p style="float: right; margin-right: 10%;">Logged in as:
				${username.username}</p>
		</div>
	</div>


	<br>
	
	<h3>List of websites</h3>

 	<div id="list2" style="overflow:auto; height:500px;">
 	<ol>
 	<c:forEach items="${TopList}" var="item">
   
  	<li><p><em>${item.product}    ${item.productUrl}</em></p></li>
    
 	</c:forEach> 
 	</ol>
 	</div>
 	
 	<br>
	<hr>	
	<!--            ADD ITEM                -->
	<button type="button" class="button-shown" data-toggle="modal"
		data-target="#myModal2">Add item to list</button>

	<!-- Modal -->
	<div id="myModal2" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
			<div class="modal-custom">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Add Product</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="/TopListan/addItem">
						<table border="0">
							<tr>
								<td>Product name:</td>
								<td><input class="textInput" type="text" name="Product"></td>
							</tr>
							<tr>
								<td>Product Url:</td>
								<td><input  class="textInput" type="text" name="Producturl"></td>
							</tr>
							<tr>
								<td colspan="2" align="center"><input class="button" type="submit"
									value="add item" /></td>
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
	</div>


	<br>
	<br>
	<!--            PAY                -->
	<button type="button" class="button-shown" data-toggle="modal"
		data-target="#myModal3" onclick="hidePayment(),showListPick()">Add value to your item</button>

	<!-- Modal -->
	<div id="myModal3" class="modal fade" role="dialog" style="margin-left:-15%;">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content" style="width:800px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Payment</h4>
				</div>
				<div class="modal-body">
				
				<div id="pickItem">
				<h4>Select product & amount</h4>

					<select id="selectBox">
						<c:forEach items="${userList}" var="item">
							<option value="${item.id}">${item.product}  ${item.productUrl}</option>
						</c:forEach>
					</select>
					
					Amount(SEK):<input type="number" id="payment-amount" oninput="checkAmountInput(this);">
					<input type="submit" id="payment-next" value="Next" class="btn btn-warning" 
					style="float:right;" onclick="changeFunc(),showPayment(),hideListPick()" disabled>
					
					
				</div>

					
					<div id="payment-div"></div>


				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				</div>
			</div>

		</div>
	</div>
	<hr>
</div>
</body>
</html>