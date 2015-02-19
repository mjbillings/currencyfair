<?php
class Trade {
	//Standard fields
	var $userId;
	var $currencyFrom;
	var $currencyTo;
	var $amountSell;
	var $amountBuy;
	var $rate;
	var $timePlaced;
	var $originatingCountry;
	
	//Meta-data
	var $timeReceived;
	var $processingStatus;
	var $timeProcessed;
	var $exception;

	function __construct() {
   		$this->processingStatus=0;
   		$this->exception = false;
	}
	
	//Perform basic input validation
	public function isValid() {
		return true;
	}

	public function populateFromArray($jsonArray) {
		if (array_key_exists('userId', $jsonArray)) {
			$this->userId = $jsonArray['userId'];
		}
		if (array_key_exists('currencyFrom', $jsonArray)) {
			$this->currencyFrom = $jsonArray['currencyFrom'];
		}
		if (array_key_exists('currencyTo', $jsonArray)) {
			$this->currencyTo = $jsonArray['currencyTo'];
		}
		if (array_key_exists('amountSell', $jsonArray)) {
			$this->amountSell = $jsonArray['amountSell'];
		}
		if (array_key_exists('amountBuy', $jsonArray)) {
			$this->amountBuy = $jsonArray['amountBuy'];
		}
		if (array_key_exists('rate', $jsonArray)) {
			$this->rate = $jsonArray['rate'];
		}
		if (array_key_exists('timePlaced', $jsonArray)) {
//			$this->timePlaced = new DateTime($jsonArray['timePlaced']);
			$this->timePlaced = $jsonArray['timePlaced'];
		}
		if (array_key_exists('currencyFrom', $jsonArray)) {
			$this->currencyFrom = $jsonArray['currencyFrom'];
		}
		if (array_key_exists('originatingCountry', $jsonArray)) {
			$this->originatingCountry = $jsonArray['originatingCountry'];
		}
	}
}
?>