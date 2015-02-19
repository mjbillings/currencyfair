<?php
	/*
	 *	Config:  Temporarily store confugration and startup information.  Some information could/shoud
	 *			be moved to property files.
	 *  Author:  Michael Billings
	*/
date_default_timezone_set('America/Chicago');

// Could use more sophisticated property mechanism and connection pooling
define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'currency_fair');
define('DB_PASSWORD', 'hire_me');
define('DB_DATABASE', 'currency_fair');
$db = new mysqli(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);
if (mysqli_connect_errno()) {
    die("Can not connect to the database: " . mysqli_connect_error());
}

//echo get_include_path();
//echo '<br/>';
//echo realpath (dirname(__FILE__));

function __autoload($class_name) {
//	echo './classes/' . $class_name . '.php';
    if(file_exists('./classes/' . $class_name . '.php')) {
        require_once('./classes/' . $class_name . '.php');   
    } else {
        throw new Exception("Unable to load $class_name.");
    }
}
?>