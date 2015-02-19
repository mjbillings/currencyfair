<?php
	/*
	 *	Messsage Consumer:  Receive JSON trades and respond with JSON response.
	 *  Author:  Michael Billings
	*/
	include("debug.php");
	include("Config.php");
	include("sql.php");
	
	$totalSharesSold = 0;
	$totalSharesBought = 0;
	$totalTrades = 0;
	
	$result = $db->query($queryTotalSharesSold);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$totalSharesSold = $row["totalSharesSold"];
	}
	
	$result = $db->query($queryTotalSharesBought);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$totalSharesBought = $row["totalSharesBought"];
	}
	
	$result = $db->query($queryTotalTrades);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$totalTrades = $row["totalTrades"];
	}
	
	$db->close();
?>
	
<html>
	<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<meta http-equiv="refresh" content="3">

		<title>Currency Fair Engineering Test - Message Frontend</title>

		<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	</head>
	<body>
		<div class="container">
			<center><h1>Currency Fair Dashboard</h1></center>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Total Shares Sold</h3>
				</div>
				<div class="panel-body"><h1 id="totalSharesSold"><?php echo $totalSharesSold ?></h1></div>
			</div>
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">Total Shares Bought</h3>
				</div>
				<div class="panel-body"><h1 id="totalSharesBought"><?php echo $totalSharesBought ?></h1></div>
			</div>
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">Total Trades</h3>
				</div>
				<div class="panel-body"><h1 id="totalTrades"><?php echo $totalTrades ?></h1></div>
			</div>
		</div>
	</body>
</html>