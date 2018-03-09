<?php

include('ISAAC.php');
include('Gamma.php');

$result = false;
$gamma = false;
if(isset($_REQUEST['key']) && isset($_REQUEST['text'])) {
	$key = iconv("UTF-8", "CP866", $_REQUEST['key']);
	$hex_mode = isset($_REQUEST['text_hex']) && $_REQUEST['text_hex'] == '1';
	if($hex_mode) {
		$text = '';
		$tmp = $_REQUEST['text'];
		for($i=0;$i<strlen($tmp)-1;$i+=2) {
			$text .= chr(hexdec($tmp[$i].$tmp[$i+1]));
		}
	} else {
		$text = iconv("UTF-8", "CP866", $_REQUEST['text']);
	}
	$gamma = isaac($key, strlen($text));
	$gammed = gamma($gamma, $text);
	if($hex_mode) {
		$result = '';
		for($i=0;$i<strlen($gammed)-1;$i+=2) {
			$result .= chr(hexdec($gammed[$i].$gammed[$i+1]));
		}
	} else {
		$result = $gammed;
	}
	$result = iconv("CP866", "UTF-8", $result);
}
?>

<html>
<head>
	<title>Gamma ISAAC</title>
	<link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="assets/css/main.css">
	<meta charset="utf-8">
</head>
<body>
	<div class="container">
		<form class="form-horizontal text-center">
		<fieldset>

		<!-- Form Name -->
		<legend>ISAAC</legend>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-2 control-label" for="key">Ключ</label>  
			<div class="col-md-8">
				<input id="key" name="key" type="text" placeholder="" class="form-control input-md" required="">
			</div>
		</div>

		<!-- Prepended checkbox -->
		<div class="form-group">
			<label class="col-md-2 control-label" for="text">Текст</label>
			<div class="col-md-8">
				<div class="input-group">
				<label class="input-group-addon"> Декодирование  
					<input type="checkbox" name="text_hex" value="1">     
				</label>
					<input id="text" name="text" class="form-control" type="text" placeholder="">
				</div>
			</div>
		</div>

		<!-- Button -->
		<div class="form-group">
			<div class="col-md-8 col-md-offset-2">
				<button type="submit" class="btn btn-primary btn-block">Отправить</button>
			</div>
		</div>
		</fieldset>
		</form>
		<div class="text-center">
			<?php if($result !== false): ?>
				<h3>Шифр: <?= $result ?></h3>
				<h4>Гамма: <?= $gamma ?></h4>
			<?php endif ?>
		</div>
	</div>
	<script src="assets/js/jquery-3.3.1.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/main.js"></script>
</body>
</html>