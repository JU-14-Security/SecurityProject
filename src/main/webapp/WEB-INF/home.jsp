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

<script type="text/javascript">


$('body').on('hidden.bs.modal', '.modal', function () {
	console.log("hehe");
    $(this).removeData('bs.modal');
  });

function hideListPick()
{
    $("#pickItem").hide();
}
function hidePayment()
{
    $("#payment-div").hide();
 
}
function showListPick()
{
    $("#pickItem").show();
 
}
function showPayment()
{
    $("#payment-div").show();
 
}
</script>
<style type="text/css">
body {
	font-family: Verdana, Geneva, sans-serif;
	background-color: #E0FFF3;
	padding: 0px;
	height: 100%;
}

#menu-header {
	border-style: solid;
	border-width: 1px;
	border-color: black;
	height: 40px;
	margin: 0px auto;
	background-color: #FFFFFF;
	color: #2879b3;
	padding-left: 25%;
	padding-top: 1%;
}

#login-form {
	float: right;
}
	ul {
		display:table; margin:0 auto;
		list-style-type: none;
	}
	
	ul li{
		background-image: url("bullet.png");
	}

#list {
	margin-left: 30%;
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

#menu-items {
	margin: 0px auto;
}
</style>
</head>
<body>
	<div id="header">
		<h1 class=stencil>TopListan</h1>
	</div>
	<div id="menu-header">
		<div id="menu-items">
						<form style="float: right; margin-right: 20%;"
				action="/TopListan/Logout">
				<input type="submit" value="Log out" />
			</form>
			<p style="float: right; margin-right: 10%;">Logged in as:
				${username.username}</p>
		</div>
	</div>


	<br>
	<div id="itemlist">
	<ul>
		<c:forEach items="${TopList}" var="item">

			<li>${item.product}    ${item.productUrl}</li>


		</c:forEach>
	</ul>
	</div>
	<br>


	
	<!--            ADD ITEM                -->
	<button type="button" class="btn btn-info" data-toggle="modal"
		data-target="#myModal2">Add item to list</button>

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
					<form method="POST" action="/TopListan/addItem">
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
								<td colspan="2" align="center"><input type="submit"
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


	<br>
	<br>
	<!--            PAY                -->
	<button type="button" class="btn btn-info" data-toggle="modal"
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
				<h2>Your submissions</h2>
					<ul>
						<c:forEach items="${userList}" var="item">
							<li>${item.product}${item.productUrl}</li>
						</c:forEach>
					</ul>
					
					
					<button class="btn btn-warning" onclick="showPayment(),hideListPick()">  Next   </button>
				</div>

					<script charset="UTF-8"
						src="https://ssl.ditonlinebetalingssystem.dk/integration/ewindow/paymentwindow.js"
						type="text/javascript"></script>
					<div id="payment-div"></div>
					<script type="text/javascript">
						paymentwindow = new PaymentWindow({
							'merchantnumber' : "8021018",
							'amount' : "5",
							'currency' : "SEK",
							'windowstate' : "2"
						});
						paymentwindow.append('payment-div');
						paymentwindow.open();
					</script>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				</div>
			</div>

		</div>
	</div>

</body>
</html>