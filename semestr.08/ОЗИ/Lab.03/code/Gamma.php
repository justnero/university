<?php

function gamma($gamma, $text) {
	$result = '';
	for($i=0;$i<strlen($text);$i++) {
		$result .= dechex(ord($gamma[$i]) ^ ord($text[$i]));
	}
	return $result;
}