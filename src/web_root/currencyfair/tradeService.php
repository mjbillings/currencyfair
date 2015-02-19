<?php
	/*
	 *	Messsage Consumer:  Receive JSON trades and respond with JSON response.
	 *  Author:  Michael Billings
	*/
	include("debug.php");
	include("Config.php");
	include("sql.php");
	
	try {
		$tradeResponse = new TradeResponse;
		$tradeResponse->status='Failure';
		$tradeResponse->timeReceived=(new DateTime())->format("Y-m-d H:i:s");

		$tradeJson = file_get_contents("php://input", true);
		$tradeArray = json_decode($tradeJson,true);

		$trade = new Trade;
		$trade->populateFromArray($tradeArray);
		$trade->timeReceived=$tradeResponse->timeReceived;

		//Valid user checks
		
		if(!$trade->isValid()) {
			$tradeResponse->status='Failure';
			$tradeResponse->errorMessage='Invalid trade';
		} else {
			//explicitly start transaction
			$db->autocommit(FALSE);
		
			$stmt = $db->prepare($insert);
			$stmt->bind_param('sssdddsssisi', $trade->userId, $trade->currencyFrom, $trade->currencyTo, $trade->amountSell, $trade->amountBuy, $trade->rate,
				$trade->timeReceived, $trade->originatingCountry, $trade->timeReceived, $trade->processingStatus, $trade->timeProcessed, $trade->exception);
			$stmt->execute();
			$stmt->close();
		
			//explicitly commit
			$db->commit();
			$db->autocommit(TRUE);

			$tradeResponse->status='Success';
			
			$db=null;
		}

//		var_dump($trade);
		
	} catch (Exception $e) {
		$tradeResponse->status='Failure';
		$tradeResponse->errorMessage=$e->getMessage();
		error_log($e->getMessage());
	}
	
	header('Content-type:application/json');
	echo json_encode($tradeResponse);

//	var_dump(json_decode($tradeJson, true));
?>