<?php
	/*
	 *	Externalize SQL statements
	 *  Author:  Michael Billings
	*/
		$insert = <<<EOD
			INSERT INTO `currency_fair`.`trade`
			(`userId`,
			`currencyFrom`,
			`currencyTo`,
			`amountSell`,
			`amountBuy`,
			`rate`,
			`timePlaced`,
			`originatingCountry`,
			`timeReceived`,
			`processingStatus`,
			`timeProcessed`,
			`exception`)
			VALUES
			(?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?)
EOD;

		$queryTotalSharesSold = 'select sum(sold)  as totalSharesSold from currency_dim';
		$queryTotalSharesBought = 'select sum(bought)  as totalSharesBought from currency_dim';
		$queryTotalTrades = 'select sum(count) as totalTrades from user_dim';

?>